# BaseFramework2
 框架共通部分抽出来，实现各种框架基础用法以及项目实际使用的方式

# 目标
       1、完成基于RxJava的基本Android代码模板 <br/>
       2、完成基于Retrofit网络请求库的代码模板 <br/>
       3、代码优化精简，方便实际项目使用 <br/>
       4、功能标准化，可以作为新项目的基础模板 <br/>

# 使用到的框架
    1、RxJava   https://github.com/ReactiveX/RxJava  基于事件编程，简化代码 教程地址：http://gank.io/post/560e15be2dca930e00da1083#toc_15 <br/>
    2、RxAndroid      https://github.com/ReactiveX/RxAndroid  基于RXJava的拓展，添加UI线程Scheduler <br/>
    3、RxLifecycle  https://github.com/trello/RxLifecycle 绑定组件的生命周期  <br/>
    4、butterknife   https://github.com/JakeWharton/butterknife 注解方式依赖注入，极大减少代码数量 <br/>
    5、EventBus https://github.com/greenrobot/EventBus 完成组件间的通信功能 <br/>
    6、ActiveAndroid https://github.com/pardom/ActiveAndroid  以对象的方式操作DB <br/>
    7、Glide https://github.com/bumptech/glide 图片操作

# 待研究
    1、Glide 高级用法，自定义信息
    2、ActivieAndroid 预导入数据和DB升级处理
    3、Retrofit 升级到2.0，api变化升级
    4、整合Volley功能
