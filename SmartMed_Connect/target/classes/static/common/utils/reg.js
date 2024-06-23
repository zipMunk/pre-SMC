/**
 * 邮箱格式正则验证
 * @param email
 * @returns {boolean}
 */
// 每个函数用于验证不同类型的输入格式是否符合特定的正则表达式规则。
function emailReg(email) {
    let emailReg = /^[\da-z]+([\-\.\_]?[\da-z]+)*@[\da-z]+([\-\.]?[\da-z]+)*(\.[a-z]{2,})+$/;
    return emailReg.test(email);
    //正则表达式对象的 test 方法，对传入的 email 参数进行正则表达式匹配。如果 email 字符串符合正则表达式的规则，则返回 true；否则返回 false。
}

/**
 * 手机号码格式正则验证
 * @param phone
 * @returns {boolean}
 */
function phoneReg(phone) {
    let phoneReg = /^1(3\d|4[5-9]|5[0-35-9]|6[567]|7[0-8]|8\d|9[0-35-9])\d{8}$/;
    return phoneReg.test(phone);
}

/**
 * 用户名正则：最长10字符，支持中文、英文
 * @param name
 * @returns {boolean}
 */
function userNameReg(name) {
    let userNameReg = /^([\u4E00-\uFA29]|[\uE7C7-\uE7F3]|[a-zA-Z]){1,10}$/;
    return userNameReg.test(name);
}

/**
 * 密码正则：8-16字符，支持英文、数字、下划线
 * @param password
 * @returns {boolean}
 */
function userPasswordReg(password) {
    let userPasswordReg = /^(\w){8,16}$/;
    return userPasswordReg.test(password);
}



