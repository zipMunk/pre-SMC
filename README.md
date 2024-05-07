# pre-SMC

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
1.在实现多轮对话基础上，添加病人信息病症相关录入，通过不断精准提问，获取病人的病症、发作时间、病史相关信息。
2.开始的提问针对的是病人一般信息，返回的提问内容是事先设置好的，减少tokens的消耗。
3.整理了一下APIservice.java的实现代码。
