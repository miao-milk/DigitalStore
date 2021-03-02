package com.mxw.job.job;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import com.google.common.hash.HashCode;
import com.mxw.common.model.entity.GoodsDO;
import com.mxw.common.model.entity.OrderDO;
import com.mxw.common.model.entity.ShopBuyerDO;
import com.mxw.common.model.entity.TradeDO;
import com.mxw.job.service.BuyerService;
import com.mxw.job.service.GoodsService;
import com.mxw.job.service.OrderService;
import com.mxw.job.service.TradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import java.util.List;


@Configuration
@EnableScheduling
public class TradeJob {


    @Autowired
    private GoodsService goodsService;

    @Autowired
    private BuyerService buyerService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private TradeService tradeService;

    //模拟新用户下单，目前数据库只有两个商家，分别在两个商家进行下单{mxw，小喵店铺}
    @Scheduled(cron = "0 0/30 * * * ? ")
    private void TradeJob() {
        System.out.println("模拟订单定时任务开始");
        String[] sellerIds = {"1", "2"};
        Integer[] sex = {1, 0};
        String sellerId = RandomUtil.randomEle(sellerIds);
        //首先查询该商家下的所有商品
        List<GoodsDO> goodList = goodsService.getGoodList(sellerId);
        //手机号码开头
        Integer[] mobile = new Integer[]{
                133, 149, 153, 173, 177, 180, 181, 189, 199,
                130, 131, 132, 145, 155, 156, 166, 175, 176, 185, 186,
                135, 136, 137, 138, 139, 147, 150, 151, 152, 157, 158, 159, 178, 182, 183, 184, 187, 188, 198
        };
        //创建新用户
        String[] names = new String[]{
                "赵", "钱", "孙", "李", "周", "吴", "郑", "王", "冯", "陈", "褚", "卫", "蒋", "沈", "韩", "杨", "朱", "秦", "尤",
                "许", "何", "吕", "施", "张", "孔", "曹", "严", "华", "金", "魏", "陶", "姜", "戚", "谢", "邹", "喻", "柏", "水",
                "窦", "章", "云", "苏", "潘", "葛", "奚", "范", "彭", "郎", "鲁", "韦", "昌", "马", "苗", "凤", "花", "方", "俞",
                "任", "袁", "柳", "酆", "鲍", "史", "唐", "费", "廉", "岑", "薛", "雷", "贺", "倪", "汤", "滕", "殷", "罗", "毕",
                "郝", "邬", "安", "常", "乐", "于", "时", "傅", "皮", "卞", "齐", "康", "伍", "余", "元", "卜", "顾", "孟", "平",
                "黄", "和", "穆", "萧", "尹", "姚", "邵", "湛", "汪", "祁", "毛", "禹", "狄", "米", "贝", "明", "臧", "计", "伏",
                "成", "戴", "谈", "宋", "茅", "庞", "熊", "纪", "舒", "屈", "项", "祝", "董", "梁", "杜", "阮", "蓝", "闵", "席",
                "季", "麻", "强", "贾", "路", "娄", "危", "江", "童", "颜", "郭", "梅", "盛", "林", "刁", "钟", "徐", "邱", "骆",
                "高", "夏", "蔡", "田", "樊", "胡", "凌", "霍", "虞", "万", "支", "柯", "昝", "管", "卢", "莫", "经", "房", "裘",
                "缪", "干", "解", "应", "宗", "丁", "宣", "贲", "邓", "郁", "单", "杭", "洪", "包", "诸", "左", "石", "崔", "吉",
                "钮", "龚", "程", "嵇", "邢", "滑", "裴", "陆", "荣", "翁", "荀", "羊", "于", "惠", "甄", "曲", "家", "封", "芮",
                "羿", "储", "靳", "汲", "邴", "糜", "松", "井", "段", "富", "巫", "乌", "焦", "巴", "弓", "牧", "隗", "山", "谷",
                "车", "侯", "宓", "蓬", "全", "郗", "班", "仰", "秋", "仲", "伊", "宫", "宁", "仇", "栾", "暴", "甘", "钭", "厉",
                "戎", "祖", "武", "符", "刘", "景", "詹", "束", "龙", "叶", "幸", "司", "韶", "郜", "黎", "蓟", "溥", "印", "宿",
                "白", "怀", "蒲", "邰", "从", "鄂", "索", "咸", "籍", "赖", "卓", "蔺", "屠", "蒙", "池", "乔", "阴", "郁", "胥",
                "能", "苍", "双", "闻", "莘", "党", "翟", "谭", "贡", "劳", "逄", "姬", "申", "扶", "堵", "冉", "宰", "郦", "雍",
                "却", "璩", "桑", "桂", "濮", "牛", "寿", "通", "边", "扈", "燕", "冀", "浦", "尚", "农", "温", "别", "庄", "晏",
                "柴", "瞿", "阎", "充", "慕", "连", "茹", "习", "宦", "艾", "鱼", "容", "向", "古", "易", "慎", "戈", "廖", "庾",
                "终", "暨", "居", "衡", "步", "都", "耿", "满", "弘", "匡", "国", "文", "寇", "广", "禄", "阙", "东", "欧", "殳",
                "沃", "利", "蔚", "越", "夔", "隆", "师", "巩", "厍", "聂", "晁", "勾", "敖", "融", "冷", "訾", "辛", "阚", "那",
                "简", "饶", "空", "曾", "毋", "沙", "乜", "养", "鞠", "须", "丰", "巢", "关", "蒯", "相", "查", "后", "荆", "红",
                "游", "郏", "竺", "权", "逯", "盖", "益", "桓", "公", "仉", "督", "岳", "帅", "缑", "亢", "况", "郈", "有", "琴",
                "归", "海", "晋", "楚", "闫", "法", "汝", "鄢", "涂", "钦", "商", "牟", "佘", "佴", "伯", "赏", "墨", "哈", "谯",
                "篁", "年", "爱", "阳", "佟", "言", "福", "南", "火", "铁", "迟", "漆", "官", "冼", "真", "展", "繁", "檀", "祭",
                "密", "敬", "揭", "舜", "楼", "疏", "冒", "浑", "挚", "胶", "随", "高", "皋", "原", "种", "练", "弥", "仓", "眭",
                "蹇", "覃", "阿", "门", "恽", "来", "綦", "召", "仪", "风", "介", "巨", "木", "京", "狐", "郇", "虎", "枚", "抗",
                "达", "杞", "苌", "折", "麦", "庆", "过", "竹", "端", "鲜", "皇", "亓", "老", "是", "秘", "畅", "邝", "还", "宾",
                "闾", "辜", "纵", "侴", "万俟", "司马", "上官", "欧阳", "夏侯", "诸葛", "闻人", "东方", "赫连", "皇甫", "羊舌",
                "尉迟", "公羊", "澹台", "公冶", "宗正", "濮阳", "淳于", "单于", "太叔", "申屠", "公孙", "仲孙", "轩辕", "令狐",
                "钟离", "宇文", "长孙", "慕容", "鲜于", "闾丘", "司徒", "司空", "兀官", "司寇", "南门", "呼延", "子车", "颛孙",
                "端木", "巫马", "公西", "漆雕", "车正", "壤驷", "公良", "拓跋", "夹谷", "宰父", "谷梁", "段干", "百里", "东郭",
                "微生", "梁丘", "左丘", "东门", "西门", "南宫", "第五", "公仪", "公乘", "太史", "仲长", "叔孙", "屈突", "尔朱",
                "东乡", "相里", "胡母", "司城", "张廖", "雍门", "毋丘", "贺兰", "綦毋", "屋庐", "独孤", "南郭", "北宫", "王孙"
        };
        String[] addressList = new String[]{
                "湖北省 咸宁市 通山县 九宫山自然保护管理局柏树下宏达汽车修理厂小巷进来第五排",
                "河北省 石家庄市 正定县 正定镇镇州北街22号（镇州饭庄院内）",
                "江苏省 南京市 六合区 大厂街道西厂门吴家洼21栋401",
                "河北省 邢台市 桥西区 钢铁路街道碧桂园別墅區216-1",
                "黑龙江省 鸡西市 鸡冠区 东风街道黑龙江省鸡西市鸡冠区东风街道电工路二妞超市",
                "辽宁省 大连市 沙河口区 李家街道三环芳草园小区10号楼2单元501",
                "海南省 海口市 龙华区 城西镇丘海大道与椰海大道红绿灯交汇处（西边北角）海口百胶贸易有限公司",
                "吉林省 长春市 南关区 永吉街道长春市全民健身活动中心，三楼健身馆前台",
                "江苏省 苏州市 吴江区 盛泽镇物流中心  F幢21--22号  黎山物流",
                "山东省 临沂市 罗庄区 傅庄街道山东宏旺实业有限公司",
                "广东省 珠海市 金湾区 红旗镇广安路401号中保新村102商铺",
                "福建省 莆田市 城厢区 灵川镇农业银行对面",
                "河南省 新乡市 辉县市 北云门镇北云门48号",
                "山东省 威海市 文登区  埠口港   镇中路管委会文登区埠口港嶅山村",
                "四川省 甘孜藏族自治州 甘孜县 甘孜镇林业小区(新区路)",
                "福建省 福州市 晋安区 秀山路63号建邦伟业广场2#楼四层",
                "江苏省 南通市 崇川区 新开街道星盛花园26幢109",
                "云南省 昆明市 五华区 莲华街道昆明市北二环贵研自然界1栋2单元401室",
                "福建省 漳州市 平和县 小溪镇商贸城25幢108号",
                "安徽省 合肥市 肥西县 圆通商厦内 纯情贝贝包行",
                "山东省 潍坊市 寿光市 圣城街道周六无人收件   东城全福元A座909室",
                "广东省 广州市 天河区 龙洞街道广东省广州市天河区龙洞街道龙洞北路314号（吉佳超市直入50米）",
                "广东省 佛山市 顺德区 勒流街道大良凤翔工业区伦桂路1号博澳城1区",
                "广东省 云浮市 罗定市 素龙街道龙华车站18号",
                "吉林省 长春市 净月旅游开发区 吉林省长春市净月开发旅游区生态大街与天工路交汇明宇广场对面轻轨车场四公司一楼门卫",
                "贵州省 毕节市 七星关区 天河路古玩城（聚缘阁）",
                "广东省 汕头市 潮阳区 和平镇紫中路宝琪服装大厦",
                "广东省 广州市 南沙区 南沙街道龙光棕榈水岸园2期34栋1701",
                "广东省 揭阳市 榕城区 梅云镇镇政府西侧雄兴饭店",
                "福建省 厦门市 思明区 嘉莲街道龙山南路汇盛花园24-2栋203室",
                "广西壮族自治区 河池市 宜州区 屏南乡板纳村周同屯",
                "福建省 漳州市 龙海市 角美镇锦宅村旺宅小区14幢110室",
                "福建省 厦门市 同安区 五显镇垵炉村垵炉里521号361100",
                "广东省 珠海市 金湾区 三灶镇金海岸社区(东咀)海华阁14栋",
                "黑龙江省 哈尔滨市 道里区 漫步巴黎14栋1单元",
                "广东省 江门市 蓬江区 白沙街道胜利路17号金发广场9楼乐巢印象前台",
                "福建省 泉州市 南安市 康美镇梅星村11组35号",
                "上海 上海市 浦东新区 康桥镇秀沿路1670弄1号A座901-902室 房掌柜",
                "浙江省 宁波市 北仑区 大碶街道大浦河北路9号6栋2号博大电子",
                "福建省 福州市 福清市 高山镇北岭村167号",
                "山东省 临沂市 罗庄区 盛庄街道沂河高都小区14号楼1单元403室",
                "吉林省 通化市 辉南县 抚民镇   自提",
                "广东省 佛山市 顺德区 大良街道锦龙路8号六楼上游国旅",
                "江苏省 连云港市 海州区 新东街道海连东路55号恒润郁洲府4幢2单元1201",
                "湖南省 娄底市 娄星区 涟滨街道涟钢大汉路桑塘街诚信驾校旁",
                "广西壮族自治区 柳州市 柳南区 南站街道广西省柳州市柳南区鹅岗一区24栋3单元",
                "江苏省 南京市 白下区 南京市白下区太平南路1号大行宫地铁站办公室",
                "山东省 淄博市 临淄区 齐都镇山东齐都药业科技园区，宏达路17号,储运部",
                "四川省 宜宾市 叙州区 柏溪镇宜宾县城北新区，拉菲，香颂花园8幢27楼1号",
                "江西省 上饶市 上饶县 清水乡清水塘新马路南山铝材店",
                "广东省 深圳市 大鹏新区 鹏飞路黄岐塘村55号",
                "广东省 肇庆市 怀集县 怀城镇国泰路75号",
                "福建省 漳州市 东山县 冬古村，海边119号",
                "广东省 清远市 清新区 太和镇明霞大道中3号A梯603",
                "广东省 汕头市 潮阳区 城南街道汕头市潮阳区城南街道新华大道金鑫加油站附近",
                "浙江省 金华市 义乌市 江东街道宗泽东路370号方艺装饰工程有限公司",
                "辽宁省 盘锦市 盘山县 羊圈子镇本街",
                "广东省 揭阳市 惠来县 神泉镇镇政府宿舍一幢4楼",
                "甘肃省 陇南市 西和县 汉源镇政务大楼123",
                "浙江省 宁波市 海曙区 环城西路北段138弄132号1幢506室（泽民阳光）",
                "广东省 佛山市 禅城区 石湾镇街道石湾镇中二路12号，佛山市恒力泰机械有限公司",
                "广西壮族自治区 防城港市 港口区 企沙镇北港大道雄风车站",
                "广东省 湛江市 雷州市 客路镇广仁街42号",
                "广东省 深圳市 福田区 桑达工业区409栋兰天路15号 518000",
                "广东省 东莞市 null 黄江镇鸡啼岗村金钱岭二街十六号金洋金属制品有限公司",
                "广东省 广州市 白云区 嘉禾街道广东省广州市白云区嘉禾街道106国道旺岭路段1号粤旺大厦C栋一楼8113档",
                "浙江省 宁波市 鄞州区 东柳街道百丈东路27号宁波华侨城明珠阁26b",
                "广西壮族自治区 防城港市 港口区 企沙镇北港大道雄风车站",
                "新疆维吾尔自治区 昌吉回族自治州 昌吉市 中山路街道健康西路名京公园1号3-2802",
                "广东省 梅州市 兴宁市 兴田街道官汕二路123号",
                "北京 北京市 西城区 广安门内街道市宣武区康乐里小区8-1206",
                "黑龙江省 哈尔滨市 道外区 南马街道景阳街-292号 崇圣电气设备有限公司",
                "广东省 佛山市 禅城区 石湾镇街道石湾镇中二路12号，佛山市恒力泰机械有限公司",
                "广东省 汕头市 金平区 东方街道菊园25幢204",
                "广东省 深圳市 南山区 西丽街道麻勘村同兴旺工业区深圳市宇盛光电有限公司",
                "浙江省 宁波市 海曙区 环城西路北段138弄132号1幢506室（泽民阳光）",
                "广西壮族自治区 南宁市 宾阳县 黎塘镇工业园区王斌相框厂",
                "湖北省 武汉市 新洲区 阳逻街道军安道168号",
        };
        //随机组成姓名
        String name = RandomUtil.randomEle(names) + RandomUtil.randomEle(names);
        //随机组成姓名
        String addres = RandomUtil.randomEle(addressList);
        String mobiles = RandomUtil.randomEle(mobile) + RandomUtil.randomNumbers(8);
        //用户表新增数据
        System.out.println("模拟订单定时任务|用户表新增数据|sellerId:"+sellerId+"|name:"+name);
        Integer sexInt = RandomUtil.randomEle(sex);
        Long shopBuyerID = buyerService.saveBuyer(sellerId, name, addres, mobiles, sexInt);


        //模拟下订单，存入订单表中
        int size = goodList.size();
        int i = RandomUtil.randomInt(0, size-1);
        GoodsDO goodsDO = goodList.get(i);
        System.out.println("模拟订单定时任务|订单表新增数据|sellerId:"+sellerId+"|name:"+name+"shopBuyerID:"+shopBuyerID+"|goodsDOID:"+goodsDO.getId());
        Integer orderId = orderService.saveOrder(sellerId, shopBuyerID, name, goodsDO.getGoodsName(), String.valueOf(goodsDO.getId()));

        //新增交易表
        System.out.println("模拟订单定时任务|交易表新增数据|sellerId:"+sellerId+"|name:"+name+"shopBuyerID:"+shopBuyerID);
        tradeService.saveTrade(sellerId,shopBuyerID,name,addres,mobiles);
        System.out.println("模拟订单定时任务结束");
    }


    public static void main(String[] args) {
//        String mobiles = RandomUtil.randomNumbers(8);
//        System.out.println(mobiles);
        HashCode hashCode = HashCode.fromLong(RandomUtil.randomLong());
        System.out.println(hashCode);
    }
}