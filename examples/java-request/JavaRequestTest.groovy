import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;

/**
 * Simple example of class to use in java request.
 */
class JavaRequestTest extends AbstractJavaSamplerClient implements Serializable {

    private String code
    private String message
    private String label

    @Override
    void setupTest(JavaSamplerContext context) {

    }

    @Override
    Arguments getDefaultParameters() {
        Arguments params = new Arguments()
        params.addArgument('code', '')
        params.addArgument('message', '')
        params.addArgument('label', '')

        return params
    }

    @Override
    SampleResult runTest(JavaSamplerContext context) {
        this.code = context.getParameter('code', '')
        this.message = context.getParameter('message', '')
        this.label = context.getParameter('label', '')

        println "JavaRequestTest $message $label"

        SampleResult results = new SampleResult()

        results.setResponseCode(this.code)
        results.setResponseMessage(this.message)
        results.setSampleLabel(this.label)
        results.setDataType(SampleResult.TEXT)

        results.sampleStart()
        results.setSuccessful(true)
        results.sampleEnd()

        return results;
    }
}