@echo off

groovy script.groovy -Vvar_host=localhost -Vvar_port=1080 -Vvar_user=john -Vvar_pass=john -Jvar_users=6 -Jvar_ramp=10 -Jvar_delay=3000 -Jvar_range=8000
