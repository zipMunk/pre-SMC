# pre-SMC

2024.6.XX. by mpz

1. 修改导航栏，底栏部分的前端界面。



2024.5.29. by wzj

1. 新增IpUtil工具类，提供获取用户Ip地址的功能

2. 新增MapUtil工具类，提供使用高德地图WebAPI服务进行的IP定位和POI搜索功能，可以根据用户所在城市，搜索当前城市内的所有综合医院

3. 新增Controller GeographyController，该Controller包含获取地理信息的接口GeoInfo，通过访问该接口可以实现上述功能，获取市内所有的综合医院。




2024.4.30. by mpz 

1. 删除了原来数据库中存储的所有疾病和药品，重新创建一些疾病和药品，上传了新的数据库脚本。 

2. 发现上传药品图片时，会自动上传到yml中设置的oss中。  

3. 更改了banner.txt文件  



2024.5.1 by zmy  

1. 修改了部分前端页面主要在feedback.html、illness.html、illness-reviews.html、index.html、medicine.html、search-illness.html、common-bar.html    

2. 更改了部分前端图片  



2024.5.3 by mpz

1. 增添了一些数据。

2. 更改了部分前端的颜色，使其更加难以辨别。等待进一步修改。



2024.5.7 by mpz

1. 修改了service类中的Apiservice.java，用笨办法实现了多轮对话功能，等待其他人改善。
2. 修改了doctor.html。

2024.5.7 by kyh
1. 在实现多轮对话基础上，添加病人信息病症相关录入，通过不断精准提问，获取病人的病症、发作时间、病史相关信息。
2. 开始的提问针对的是病人一般信息，返回的提问内容是事先设置好的，减少tokens的消耗。
3. 整理了一下APIservice.java的实现代码。

2024.5.7 by kyh
1. 完善一般信息的多轮精确咨询。
2. 下一步想办法通过用户的咨询判断用户是否在问诊，如果是询问病情相关信息才触发问诊模式，并且重置病人信息类，清空上次的病情记录。

2024.5.7 by kyh
1. 完成了轮询问诊模式的智能触发，并且选择了一个合适触发阈值和相关参数，但是由于模型不够智能，只要出现了疾病的词语就会误触，所有的更改都在ApiService。
2. 如果进入第二次智能问诊模式，前一次智能问诊模式收集的病人信息会被覆盖。
3. 目前用户缺乏中途退出智能问诊模式的选择

2024.5.7 by mpz

1. 注意，前端所使用的bootstrap版本为5.0.2。
1. 修改了illness.html，doctor.html，custom.js，修改了智慧问诊界面，修复了一些小bug。

2024.5.29 by mpz

1. 修改了doctor.html。
2. 阿里云天天给我发安全警报邮件，很烦。application.yml现在已改成加密模式。运行程序时请用群文件中的yml替换。

2024.6.20 by kyh

1.完善了登录和注册的后端实现，登录和注册之后的用户信息已经保存下来，直接调用get方法即可获取相应用户信息（userController.loginUser）。

2.添加了病史的数据库表（需要重新运行sql文件），新增实现病史的实体类 PatientHistory.java，相应的DAO接口 PatientHistoryDao.java，服务类 PatientHistoryService.java 控制器类 PatientHistoryController.java。

3.注释了BaseController.java的ApiService 自动注入，避免了bean的循环依赖。

4.大改了ApiService.java服务类，触发智能问诊模式之后会自动保存相应的病史信息，同时给出问诊结果的时候会自动整合病史和当前的症状，从而给出更加合理的诊断结果。




