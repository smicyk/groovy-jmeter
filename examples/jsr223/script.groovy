@GrabConfig(systemClassLoader=true)
@Grab('net.simonix.scripts:groovy-jmeter')

@groovy.transform.BaseScript net.simonix.dsl.jmeter.TestScript script

start {
    plan {
        arguments {
            argument(name: 'var_jmeter_variable', value: 'jmeter')
        }

        group(users: 1000, rampUp: 100) {
            // simple inline script (groovy by default)
            jsrsampler 'inline jsr sampler', inline: '''\
                log.info("Create jmeter variable in inline script, thread=${Thread.currentThread().getId()}")
                vars.put('var_inline_variable', 'INPUT DATA FOR NEXT PREPROCESSOR')
            '''.stripIndent()

            // use external files with groovy code
            jsrsampler file: 'groovy/sampler.groovy', {
                jsrpreprocessor file: 'groovy/preprocessor.groovy'
                jsrpostprocessor file: 'groovy/postprocessor.groovy'
            }

            // inline script with parameters (
            // - first parameter is dynamic and injected by groovy during test plan build
            // - second parameter is normal jmeter variable and is injected by jmeter during test execution)
            // - third parameter is hardcoded value
            // note the difference in quotes
            jsrsampler inline: '''\
                log.info("Sampler with parameters=${args}")
            '''.stripIndent(), parameters: [ "${var_groovy_variable}", '${var_jmeter_variable}', 'John' ]

            // inline script with groovy injected value
            // variable is injected by groovy during test plan build
            // note the difference in quotes
            jsrsampler inline: """\
                log.info("Sampler with groovy variable '${var_groovy_variable}'")
            """.stripIndent()

            // timer inside element affects parent element only
            flow action: 'pause', duration: 0, {
                // jsr sampler for timer
                jsrtimer 'simple timer', inline: '''\
                    log.info("Wait 1 sec")
                    return 1000
                '''.stripIndent()
            }

            // assertion for sampler (this sampler should be marked as failed)
            jsrsampler inline: '''return "SAMPLE FAILS"''', {
                jsrassertion inline: '''\
                    if(SampleResult.responseDataAsString == 'SAMPLE FAILS') {
                        AssertionResult.failureMessage = 'SAMPLE FAILS'
                        AssertionResult.failure = true
                    }
                '''.stripIndent()
            }

            // listener for all samplers events, prints output on the terminal
            jsrlistener inline: '''\
                OUT.println("Hostname: $sampleEvent.hostname, Group: $sampleEvent.threadGroup, Time: $sampleEvent.result.time")
            '''.stripIndent()
        }

        summary(file: 'script.jtl', enabled: true)
    }
}
