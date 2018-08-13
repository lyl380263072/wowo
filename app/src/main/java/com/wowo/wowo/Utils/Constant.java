
package com.wowo.wowo.Utils;

/**
 * author: ZhangXiaoWei
 * email : cherno@126.com
 * blog  : http://www.jianshu.com/users/0f532bf88454/latest_articles
 * time  : 2017/11/28
 * desc  :
 */
public interface Constant {

    class Url {
        public static final String WX_APP_ID = "wxc6c21adf1eb4adc7";


        /**
         * 测试服务器地址
         */
//        public static final String BASE = "http://120.55.54.230/api/";

        /**
         * 本地服务器(用流量也可以测试)
         */

//        public static final String BASE = "http://api.ngrok.xiaomiqiu.cn/api/";


        /**
         * 本地服务器
         */


        //正式服务器
        public static final String BASE = "http://api.infinite-yx.com:9091/api/";


        /**
         * 登录页面接口
         */
        public static final String LOGIN = "user/register";//用户注册
        public static final String ADDJPUSH = "jpush/add";//极光推送添加注册号,别名
        public static final String ISREGISTER = "user/isRegister";//用户是否注册过
        public static final String GETSERVICE = "share/getService";//服务详情分享
        public static final String GETDEMAND = "share/getDemand";//需求详情分享


        /**
         * 服务页面接口
         */
        public static final String RELEASESERVICE = "service/push";//发布服务
        public static final String RELEASEREQUEST = "demand/push";//发布需求
        public static final String HAVESKILL = "service/haveSkill";//判断是否重复发过同个品类的接口
        public static final String QUERYLOCATION = "service/queryByLocation";//定位查询周围服务
        public static final String QUERYBYSERVICESKILL = "service/queryByServiceSkill";//技能标签查询服务列表
        public static final String ADDSERVICE = "collect/add";//收藏服务
        public static final String ADDCOMPLAIN = "complain/addComplain";//举报
        public static final String SERVICELIST = "service/list";//个人所有技能列表
        public static final String ALLDICTIONARY = "dictionary/all";//所有分类
        public static final String GETTOKEN = "rongyun/getToken";//获取融云token
        public static final String GETPICANDNICK = "user/picByUserId";//userId查询头像和昵称
        public static final String QUERYSERVICEINFO = "service/queryServiceInfo";//查询某一个服务详情
        public static final String DELETECOLLECT = "collect/delete";//删除收藏
        public static final String RECOMMENDSERVICEBYHISTORY = "search/recommendServiceByHistory";//个性推荐的服务
        public static final String GETBANNER = "dictionary/getBanner";//轮播图
        public static final String NOTICE = "search/notice";//小喇叭信息
        public static final String ADDSERVICESEARCHHISTORY = "search/addServiceSearchHistory";//添加服务搜索记录


        /**
         * 需求页面接口
         */
        public static final String QUERYDEMANDINFO = "demand/queryDemandInfo";//查询一个需求详情
        public static final String RECOMMENDDEMANDBYHISTORY = "search/recommendDemandByHistory";//个性推荐的需求
        public static final String QUERYREQUESTBYLOCATION = "demand/queryByLocation";//查询周围需求
        public static final String LISTBYSKILL = "demand/listBySkill";//标签查询需求列表
        public static final String REQUESTLIST = "demand/list";//需求详情列表
        public static final String ADDDEMANDSEARCHHISTORY = "search/addDemandSearchHistory";//添加需求搜索记录

        /**
         * 消息页面接口
         */
        public static final String GETMESSAGE = "message/getMessage";//系统消息
        public static final String UPDATEMESSAGE = "message/updateMessage";//更改为已读状态


        /**
         * 个人中心页面接口
         */
        public static final String USERUPDATE = "user/update";//用户修改信息
        public static final String MODIFY = "user/updatePhone";//修改手机号
        public static final String AUTHENTICATION = "user/authentication";//实名认证
        public static final String UMONEYCHARGE = "wallet/uMoneyCharge";//U币充值
        public static final String COLLECTLIST = "collect/list";//收藏列表
        public static final String ISAUTHENTICATION = "user/booleanAuthentication";//判断用户是否实名认证过
        public static final String SELECTCOUNT = "user/selectCount";//个人中心服务,需求,收藏,订单数量
        public static final String ADDACCOUNTNUMBER = "pay/addAccountNumber";//添加账号
        public static final String DELETEDEMANDINFO = "demand/deleteDemandInfo";//删除需求
        public static final String DELETEPIC = "photo/delete";//删除图片
        public static final String DELETESKILL = "service/deleteServiceInfo";//删除技能
        public static final String SECURITYCHARGE = "wallet/securityMoneyCharge";//保证金充值
        public static final String MONEYLIST = "wallet/moneyList";//我的收入账单列表
        public static final String UMONEYCHARGELIST = "wallet/uMoneyChargeList";//u币充值账单列表
        public static final String ACCOUNTNUMBER = "pay/accountNumberList";//所有账号列表
        public static final String ACCOUNTINFO = "wallet/listAccountInfo";//个人账户信息 u币,余额,保证金
        public static final String ORDLIST = "order/list";//个人所有订单
        public static final String QUERYUMONEY = "wallet/queryUmoney";//用户U币余额
        public static final String FEEDBACK = "feedback/add";//意见反馈
        public static final String SECURITYMONEYCHARGE = "wallet/securityMoneyCharge";//保证金充值
        public static final String ACCOUNTNUMBERLIST = "pay/accountNumberList";//所有账号列表
        public static final String DELETEACCOUNTNUM = "pay/deleteAccountNumber";//删除账号
        public static final String EXTRACTMONEY = "wallet/extractMoney";//提现
        public static final String EXTRACTSECURITYMONEY = "wallet/extractSecurityMoney";//保证金提现
        public static final String QUERYINVITECODE = "user/queryInviteCode";//验证邀请码是否存在
        public static final String GETINVITECODE = "user/getInviteCode";//获取用户邀请码
        public static final String ADDINVITECODE = "user/addInviteCode";//添加邀请码


        /**
         * 订单页面接口
         */
        public static final String CREATEORDER = "order/createOrder";//生成订单
        public static final String ORDERINFO = "order/orderinfo";//订单详情
        public static final String ORDERGRADED = "order/orderGraded";//订单评价
        public static final String APPLYFORVIP = "pay/applyForVip";//申请vip
        public static final String SERVICEORDERLISTBYSTATUS = "order/serviceOrderListBystatus";//服务方不同状态的订单列表
        public static final String ORDERREJECTBYSERVICEUSER = "order/orderRejectByserviceUser";//服务方拒绝接单
        public static final String ORDERRECEIVEBYSERVICEUSER = "order/orderReceiveByserviceUser";//服务方接单
        public static final String ORDERFINISHEDBYSERVICEUSER = "order/orderfinishedByserviceUser";//服务方确认完成
        public static final String ORDERREFUNDINFO = "order/orderRefundInfo";//需求方申请退款说明
        public static final String ORDERREJECT = "order/orderReject";//服务方拒绝退款提交
        public static final String ORDERREFUNDAGREEDBYSERVICEUSER = "order/orderRefundAgreedByServiceUser";//服务方同意退款
        public static final String DEMANDORDERLISTBYSTATUS = "order/demandOrderListBystatus";//需求方不同状态的订单列表
        public static final String ORDERREJECTBYDEMANDUSER = "order/orderRejectBydemandUser";//需求方 超时未付,订单取消
        public static final String ORDERREFUND = "order/orderRefund";//需求方申请退款信息上传
        public static final String ORDERFINISHEDBYDEMANDUSER = "order/orderfinishedBydemandUser";//需求方确认订单完成
        public static final String ORDERPAYMENTBYDEMANDUSER = "order/orderPaymentBydemandUser";//需求方付款
        //        public static final String UPDATEUMONEY = "wallet/updateUmoney";//U币充值校验
//        public static final String UPDATESECURITYMONEY = "wallet/updateSecurityMoney";//保证金充值校验
        public static final String UPDATEORDER = "order/updateOrder";//订单支付校验


    }


}
