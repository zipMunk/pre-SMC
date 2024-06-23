// 前端负责用户界面和用户交互，收集用户输入并通过Ajax请求将数据发送到后端。
// 后端接收请求后，执行相应的业务逻辑，并将结果返回给前端。
// 前端根据后端返回的结果，进行页面的更新或提示用户。

/**
 * 发送验证码
 */
function sendEmailCode() {
    // 获取用户输入的邮箱
    //使用了jQuery来获取用户在输入框（id为userEmail）中输入的值。
    // .val()方法返回输入框的当前值，并将其存储在变量email中。
    let email = $('#userEmail').val();
    // 如果邮箱为空，提示用户并返回
    if (!email) {
        layer.msg("邮箱不能为空");
        return;
    }
    if (!emailReg(email)) {
        layer.msg("请输入正确的邮箱地址");
        return;
    }
    // 发送Ajax请求给服务器，请求发送邮箱验证码
    // jQuery 提供的用于发送 AJAX 请求的函数。
    // 接受一个包含各种选项的 JavaScript 对象作为参数，这些选项定义了 AJAX 请求的各个方面。
    $.ajax({
        type: "POST",// 请求方式为 POST
        url: "login/sendEmailCode",// 向 "login/sendEmailCode" 发送请求，这是服务器端的处理接口
        data: {
            email: email,// 发送的数据，这里是用户输入的邮箱地址
        },
        dataType: "json",//指定了预期从服务器返回的数据类型。在这里，指定返回的数据应该是 JSON 格式的。
        success: function (data) {// 请求成功的回调函数，data 是服务器返回的数据
            // 根据服务器返回的消息进行处理
            if (data['code'] !== 'SUCCESS') {// 如果服务器返回的状态码不是 'SUCCESS'
                layer.msg(data['message'])// 使用 layer.msg 方法显示服务器返回的消息
            } else {// 如果服务器返回的状态码是 'SUCCESS'
                // 如果成功，禁用按钮并开始倒计时60秒
                time("#email-code", 60);// 调用 time 函数开始倒计时，传入倒计时目标元素和秒数
            }
        }
    });
}

/**
 * 注册
 */
function register() {
    // 获取用户输入的注册信息
    let userAccount = $('#userAccount').val();//$('#userAccount') 选择器表示选取 id 为 userAccount 的元素。# 表示选择 id，后面跟着 id 的名称。$ 是 jQuery 的全局函数。它允许您使用 CSS 选择器来选取 HTML 元素。
    let userName = $('#userName').val();
    let userPwd = $('#userPwd').val();
    let userTel = $('#userTel').val();
    let userAge = $('#userAge').val();
    let userSex = $('#userSex').find("option:selected").val();
    let userEmail = $('#userEmail').val();
    let code = $('#code').val();

    // 如果有任何输入信息为空，提示用户并返回
    if (!userAccount || !userName || !userPwd || !userTel || !userAge || !userEmail || !code) {
        layer.msg("请完整填写信息");
        return;
    }

    // 发送Ajax请求给服务器，请求注册用户
    $.ajax({
        type: "POST",
        url: "login/register",
        data: {
            userAccount: userAccount,
            userName: userName,
            userPwd: userPwd,
            userTel: userTel,
            userAge: userAge,
            userSex: userSex,
            userEmail: userEmail,
            code: code,
        },
        dataType: "json",
        success: function (data) {
            // 根据服务器返回的消息进行处理，并显示消息给用户
            layer.msg(data['message']);
            // 如果注册成功，延时2秒后刷新页面
            if (data['code'] === 'SUCCESS') {
                setTimeout('reload()', 2000);
            }
        }
    });

}

/**
 * 登录
 */
function login() {
    // 获取用户输入的登录信息
    let loginAccount = $('#loginAccount').val();
    let loginPassword = $('#loginPassword').val();
    // 如果账号或密码为空，提示用户并返回
    if (!loginAccount || !loginPassword) {
        layer.msg("请完整登录信息");
        return;
    }
    // 发送Ajax请求给服务器，请求用户登录
    $.ajax({
        type: "POST",
        url: "login/login",
        data: {
            userAccount: loginAccount,
            userPwd: loginPassword
        },
        dataType: "json",
        success: function (data) {
            // 根据服务器返回的消息进行处理，并显示消息给用户
            layer.msg(data['message']);
            // 如果登录成功，延时2秒后刷新页面
            if (data['code'] === 'SUCCESS') {
                setTimeout('reload()', 2000);
            }
        }
    });

}

/**
 * 修改资料
 */
function updateProfile() {
    let id = $('#userId').val();
    let userName = $('#userName').val();
    let userTel = $('#userTel').val();
    let userAge = $('#userAge').val();
    let imgPath = $('#img').val();

    if (!userName || !userTel || !userAge) {
        layer.msg("请完整填写信息");
        return;
    }

    $.ajax({
        type: "POST",
        url: "user/saveProfile",
        data: {
            id: id,
            userName: userName,
            userTel: userTel,
            userAge: userAge,
            imgPath: imgPath,
        },
        dataType: "json",
        success: function (data) {
            layer.msg(data['message']);
            if (data['code'] === 'SUCCESS') {
                setTimeout('reload()', 2000);
            }
        }
    });
}

/**
 * 上传头像
 */
function uploadPhoto() {
    // 创建一个 FormData 对象，用于存储上传的文件数据
    var formdata = new FormData();
    // 获取页面中 id 为 img-file 的文件输入框的第一个文件，并将其添加到 FormData 中
    formdata.append("file", $("#img-file").get(0).files[0]);
    // 发起 AJAX 请求
    $.ajax({
        async: false,// 设置同步请求（不推荐使用，一般应使用异步请求）
        type: "POST",// 请求类型为 POST
        url: "file/upload",// 请求的 URL 地址，上传文件的接口
        dataType: "json",// 服务器返回的数据类型为 JSON 格式
        data: formdata,// 发送的数据为 FormData 对象，包含上传的文件
        contentType: false,//ajax上传图片需要添加,设置 contentType 为 false，因为 FormData 已经包含了正确的 Content-Type
        processData: false,//ajax上传图片需要添加,设置 processData 为 false，因为不需要 jQuery 对 FormData 进行处理
        success: function (data) {
            // 成功回调函数，处理服务器返回的数据
            console.log(data); // 在控制台打印服务器返回的数据
            layer.msg(data['message']);// 使用 layer.msg() 方法显示服务器返回的消息
            $("#img-preview").attr('src', data['data']);// 将返回的图片地址设置为页面中 id 为 img-preview 的元素的 src 属性值
            $("#img").attr('value', data['data']); // 将返回的图片地址设置为页面中 id 为 img 的元素的 value 属性值
        }
    });
}

/**
 * 修改资料
 */
function updatePassword() {
    let oldPass = $('#oldPassword').val();
    let password1 = $('#password1').val();
    let password2 = $('#password2').val();

    if (!oldPass || !password1 || !password2) {
        layer.msg("请完整填写信息");
        return;
    }

    if (password1 !== password2) {
        layer.msg("两次密码不一致");
        return;
    }

    $.ajax({
        type: "POST",
        url: "user/savePassword",
        data: {
            oldPass: oldPass,
            newPass: password1,
        },
        dataType: "json",
        success: function (data) {
            layer.msg(data['message']);
            if (data['code'] === 'SUCCESS') {
                setTimeout('reload()', 1000);
            }
        }
    });
}

/**
 * 60秒倒计时
 * @param o
 */
function time(o, wait) {
    if (wait === 0) {
        $(o).attr("disabled", false);
        $(o).html("获取");
    } else {
        $(o).attr("disabled", true);
        $(o).html(wait + "秒");
        wait--;
        setTimeout(function () {
            time(o, wait);
        }, 1000);
    }
}

/**
 * 刷新页面
 */
function reload() {
    window.location.reload();
}

/**
 * 跳转到指定页面
 * @param url
 */
function reloadTo(url) {
    let appCnName = APPLICATION_EN_NAME();
    let href = window.location.href;
    href = href.split("/" + appCnName)[0] + "/" + appCnName + url;
    window.location.href = href;
}

/**
 * 跳转到指定页面
 * @param url
 */
function reloadToGO(url) {
    window.location.href = url;
}

/**
 * 分享网站
 */
function share() {
    alert("请复制链接后分享：" + window.location.href);
}

/**
 * 提交反馈
 */
function feedback() {
    let name = $('#name').val();
    let email = $('#email').val();
    let title = $('#title').val();
    let content = $('#content').val();

    if (!name || !email || !title || !content) {
        layer.msg("请完整填写信息");
        return;
    }

    $.ajax({
        type: "POST",
        url: "feedback/save",
        data: {
            name: name,
            email: email,
            title: title,
            content: content,
        },
        dataType: "json",
        success: function (data) {
            layer.msg(data['message']);
            if (data['code'] === 'SUCCESS') {
                setTimeout('layer.msg(\'我们已经收到了你的反馈，感谢您的支持！\');', 2000);
                setTimeout('reload()', 4000);
            }
        }
    });
}

/**
 * 保存疾病
 */
function saveIllness() {
    let id = $('#id').val();
    let illnessName = $('#illnessName').val();
    let includeReason = $('#includeReason').val();
    let illnessSymptom = $('#illnessSymptom').val();
    let specialSymptom = $('#specialSymptom').val();
    let kindId = $('#kindId').find("option:selected").val();

    $.ajax({
        type: "POST",
        url: "illness/save",
        data: {
            id: id,
            illnessName: illnessName,
            includeReason: includeReason,
            illnessSymptom: illnessSymptom,
            specialSymptom: specialSymptom,
            kindId: kindId,
        },
        dataType: "json",
        success: function (data) {
            layer.msg(data['message']);
            if (data['code'] === 'SUCCESS') {
                setTimeout('reload()', 2000);
            }
        }
    });
}

/**
 * 保存药品
 */
function saveMedicine() {
    let id = $('#id').val();
    let imgPath = $('#img').val();
    let medicineName = $('#medicineName').val();
    let keyword = $('#keyword').val();
    let medicineBrand = $('#medicineBrand').val();
    let medicinePrice = $('#medicinePrice').val();
    let medicineEffect = $('#medicineEffect').val();
    let interaction = $('#interaction').val();
    let taboo = $('#taboo').val();
    let usAge = $('#usAge').val();
    let medicineType = $('#medicineType').find("option:selected").val();

    $.ajax({
        type: "POST",
        url: "medicine/save",
        data: {
            id: id,
            imgPath: imgPath,
            medicineName: medicineName,
            keyword: keyword,
            medicineBrand: medicineBrand,
            medicinePrice: medicinePrice,
            medicineEffect: medicineEffect,
            interaction: interaction,
            taboo: taboo,
            usAge: usAge,
            medicineType: medicineType,
        },
        dataType: "json",
        success: function (data) {
            layer.msg(data['message']);
            if (data['code'] === 'SUCCESS') {
                setTimeout('reload()', 2000);
            }
        }
    });
}

/**
 * 删除疾病
 * @param id
 */
function deleteIllness(id) {
    $.ajax({
        type: "POST",
        url: "illness/delete",
        data: {
            id: id,
        },
        dataType: "json",
        success: function (data) {
            layer.msg(data['message']);
            if (data['code'] === 'SUCCESS') {
                setTimeout('reload()', 2000);
            }
        }
    });
}

/**
 * 删除药品
 * @param id
 */
function deleteMedicine(id) {
    $.ajax({
        type: "POST",
        url: "medicine/delete",
        data: {
            id: id,
        },
        dataType: "json",
        success: function (data) {
            layer.msg(data['message']);
            if (data['code'] === 'SUCCESS') {
                setTimeout('reload()', 2000);
            }
        }
    });
}

/**
 * 保存关系
 * @param illnessId
 * @param medicineId
 */
function saveIllnessMedicine(illnessId, medicineId) {
    $.ajax({
        type: "POST",
        url: "illness_medicine/save",
        data: {
            illnessId: illnessId,
            medicineId: medicineId,
        },
        dataType: "json",
        success: function (data) {
            layer.msg('修改成功');
        }
    });
}

/**
 * 删除关系
 * @param id
 */
function deleteIllnessMedicine(id) {
    $.ajax({
        type: "POST",
        url: "illness_medicine/delete",
        data: {
            id: id,
        },
        dataType: "json",
        success: function (data) {
            layer.msg('修改成功');
        }
    });
}

/**
 * 删除反馈
 * @param id
 */
function deleteFeedback(id) {
    $.ajax({
        type: "POST",
        url: "feedback/delete",
        data: {
            id: id,
        },
        dataType: "json",
        success: function (data) {
            layer.msg(data['message']);
            if (data['code'] === 'SUCCESS') {
                setTimeout('reload()', 2000);
            }
        }
    });
}

/**
 * 初始化聊天窗口滚动条
 */
function messageInit() {
    let height = $("#messages")[0].scrollHeight;
    $("#messages").scrollTop(height);
}

/**
 * 发送消息
 */
function send() {
    let message = $('#message').val();
    if (!message) {
        return;
    }
    $('#messages').append("<div class='msg-received msg-sent' style=\"margin-right: 20px\"><div class='msg-content'><p>现在</p><p class='msg'>" + message + "</p></div></div>");
    messageInit();
    $('#message').val('');
    $.ajax({
        type: "POST",
        url: "message/query",
        data: {
            content: message,
        },
        dataType: "json",
        success: function (data) {
            if (data['code'] === 'SUCCESS') {
                message = data['message'];
                $('#messages').append("<div class=\"msg-received\">\n" +
                    "                   <div class=\"msg-image\">\n" +
                    "                      <img src=\"assets/images/team/doctor.png\" alt=\"image\">\n" +
                    "                   </div>\n" +
                    "                   <div class=\"msg-content\">\n" +
                    "                      <p>现在</p>\n" +
                    "                      <p class=\"msg\">\n" + message +
                    "                      </p>\n" +
                    "                   </div>\n" +
                    "                  </div>");
                messageInit();
            }
        }
    });

}

/**
 * 发送消息
 */
function smart_send() {
    let message = $('#message').val();
    if (!message) {
        return;
    }
    $('#messages').append("<div class='msg-received msg-sent' style=\"margin-right: 20px\"><div class='msg-content'><p>现在</p><p class='msg'>" + message + "</p></div></div>");
    messageInit();
    $('#message').val('');
    $.ajax({
        type: "POST",
        url: "message/smart_query",
        data: {
            content: message,
        }, // 发起一个POST请求到服务器端点message/query，传递用户发送的消息作为数据。请求的数据类型设置为JSON。
        dataType: "json",
        success: function (data) {
            if (data['code'] === 'SUCCESS') {
                message = data['message'];
                $('#messages').append("<div class=\"msg-received\">\n" +
                    "                   <div class=\"msg-image\">\n" +
                    "                      <img src=\"assets/images/team/doctor.png\" alt=\"image\">\n" +
                    "                   </div>\n" +
                    "                   <div class=\"msg-content\">\n" +
                    "                      <p>现在</p>\n" +
                    "                      <p class=\"msg\">\n" + message +
                    "                      </p>\n" +
                    "                   </div>\n" +
                    "                  </div>");
                messageInit();
            }
        }
    });

}

/**
 * 搜索病
 */
function searchGroup(kind) {
    let content = $("#search").val().trim();
    if (content == ""){
        xtip.msg("请输入查询内容");
        return;
    }
    let href = window.location.href;
    href = href.split("/")[0] + "/findIllness?illnessName="+content+"&kind="+kind;
    reloadToGO(href);
}

/**
 * 搜索病
 */
function searchGroupByName() {
    let content = $("#search").val().trim();
    if (content == ""){
        xtip.msg("请输入查询内容");
        return;
    }
    let href = window.location.href;
    href = href.split("/")[0] + "/findIllness?illnessName="+content;
    reloadToGO(href);
}

/**
 * 搜索一下
 */
function searchGlobalSelect() {
    let content = $("#cf-search-form").val().trim();
    if (content == ""){
        xtip.msg("请输入查询内容");
        return;
    }
    let href = window.location.href;
    alert(href);
    href = href.split("/")[0] + "/findIllness?illnessName="+content;
    reloadToGO(href);
}
/**
 * 搜索药
 */
function searchMedicine() {
    let content = $("#search-medicine").val().trim();
    if (content == ""){
        xtip.msg("请输入查询内容");
        return;
    }
    let href = window.location.href;
    href = href.split("/")[0] + "/findMedicines?nameValue="+content;
    reloadToGO(href);
}

