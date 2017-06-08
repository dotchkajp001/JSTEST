rem postgres jarの最新化

rem 環境設定
cf set-env aplitest01 APP_BASE /home/vcap/app
cf set-env aplitest01 INSTALL_CONTEXTS postgresql,
cf set-env aplitest01 JBP_CONFIG_TOMCAT "{tomcat:{context_path:/ge}}"


https://github.com/cloudfoundry/java-buildpack/blob/master/docs/container-tomcat.md

otmygesample01
cf set-env otmygesample01 APP_BASE /home/vcap/app
cf set-env otmygesample01 INSTALL_CONTEXTS mysql,
cf set-env otmygesample01 JBP_CONFIG_TOMCAT "{tomcat : {context_path : /ge}}"


rem ログイン
cf login -a https://api.ng.bluemix.net -u ﾕｰｻﾞ名 -p ﾊﾟｽﾜｰﾄﾞ
cf push <appname> -p <war名> -b java_buildpack

cf login -a https://api.ng.bluemix.net -u highpot24@yahoo.co.jp
cf login -a https://api.ng.bluemix.net -u highpot12@yahoo.co.jp -p Tuna1234

cf push aplitest01 -p ge.war -b https://github.com/otsample/java-buildpack.git
cf push otsample02 -p ge.war -b https://github.com/otsample/java-buildpack.git
