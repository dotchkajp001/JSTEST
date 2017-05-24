set http_proxy=http://mtc-px14:8081
set https_proxy=http://mtc-px14:8081
set JAVA_HOME=H:\opengion_user\apps\jdk\jdk1.8.0_77

rem maven情報の設定
set PATH=%PATH%;H:\opengion_user\download\apache-maven-3.5.0\bin;
cd /d %~dp0

rem ﾃｽﾄ用ﾌｫﾙﾀﾞ移動
cd test
