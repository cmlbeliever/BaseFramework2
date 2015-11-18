# DB层采用ActiveAndroid框架处理
... 实现功能
   1、App安装时执行初始化sql文件
   2、App升级时执行升级sql文件
# app安装时执行sql文件
 1、在Manifest文件上添加配置 <meta-data android:name="AA_DB_NAME" android:value="mydemo.db"/>
 2、ActiveAndroid 会根据配置将assets目录下的mydemo.db作为初始化db
 3、官网url：https://github.com/pardom/ActiveAndroid/wiki/Pre-populated-databases

# app升级执行sql文件
 1、官网：https://github.com/pardom/ActiveAndroid/wiki/Schema-migrations
 2、在assets/migrations目录下新建${dbversion}.sql文件
 3、ActiveAndroid 会执行大于之前版本，小于等于当前版本的sql文件
 4、记得在manifest文件上配置