package com.hycf.example.hsp.api;

import com.alibaba.fastjson.JSON;
import com.hycf.example.hsp.bean.Result;

import java.util.Random;

/**
 * 模拟数据
 */
public class DatasUtil {
    /**
     * 获取列表数据
     * @return
     */
	public static Result getZoneListDatas(){
        String str="{\"status\":\"1\",\"msg\":{\"page\":{\"sort\":\"\",\"rownum_\":0,\"order\":\"\",\"totalCount\":13,\"page\":1,\"toNumber\":10,\"fromNumber\":0,\"totalPage\":2,\"rows\":10},\"list\":[{\"appointUserid\":0,\"createTime\":1471943240000,\"icon\":\"Image/20160819/1471570856669.jpeg\",\"takeTimes\":\"12\",\"goodjobCount\":0,\"replys\":[],\"replyCount\":0,\"pictures\":\"\",\"type\":\"0\",\"goodjobs\":[],\"isvalid\":\"0\",\"content\":\"生活赋予我们一种巨大的和无限高贵的礼品，这就是青春：充满着力量，充满着期待志愿，充满着求知和斗争的志向，充满着希望信心和青春。\\n勇敢坚毅真正之才智乃刚毅之志向最糟糕的是人们在生活中经常受到错误志向的阻碍而不自知，真到摆脱了那些阻碍时才能明白过来。\\n\\n\n" +
                "真正的才智是刚毅的志向\",\"id\":15,\"appointUserNickname\":\"\",\"nickName\":\"MateralDesign\",\"address\":\"\",\"userId\":10000,\"longitude\":\"0\",\"latitude\":\"0\"},{\"appointUserid\":0,\"createTime\":1471942968000,\"icon\":\"Image/20160819/1471570856669.jpeg\",\"takeTimes\":\"12\"," +
                "\"goodjobCount\":0,\"replys\":[],\"replyCount\":0,\"pictures\":\"Image/20180517/1471942933390.1471865953838.jpeg;Image/20180517/1471942933488.jpeg;Image/20180517/1471942934413.jianshu.haruki.jpeg;Image/20180517/1471942935325.jpeg\",\"type\":\"0\",\"goodjobs\":[],\"isvalid\":\"0\",\"content\":\"“毕业既，觅卧榻于公司之旁，以金钱易时间，不忘尔志，不贪闲逸，不改己心，不废学习，则大事可图”。每日朝九晚九工作，下班亦不敢松弛。读书以固基础，观源码以长见闻，勤实践以强逻辑，阅博客以知明细。至今6月余，设计模式、jvm、算法、并发、java8皆熟记于心，dubbo、zookeeper、mq、redis、mysql亦游刃有余。弟子观时机已至，断然离职，先面京东，又图58，再战阿里。无奈仅工作半年，虽技术面皆过，却被hr背调所弃。某日夜深，徘徊京城街巷，困惑失意，百感交集。忽观微信朋友圈，谆谆教诲直刺心间，热血之心再起，次日既面美团，终一锤定音。今日终获美团offer，忆奋斗之历程，激动难耐，今以字记之。\",\"id\":14,\"appointUserNickname\":\"\",\"nickName\":\"MateralDesign\",\"address\":\"\",\"userId\":10000,\"longitude\":\"0\",\"latitude\":\"0\"},{\"appointUserid\":0,\"createTime\":1471398857000,\"icon\":\"Image/20180517/1471398965572.jpeg\",\"takeTimes\":\"14\",\"goodjobCount\":1,\"replys\":[{\"id\":22,\"content\":\"别发了,请体谅下我的身体.\",\"createTime\":1471399614000,\"appointUserid\":0,\"publishId\":13,\"appointUserNickname\":\"\",\"userId\":10005,\"pictures\":\"\",\"userNickname\":\"小迷妹\"},{\"id\":23,\"content\":\"么么哒!.\",\"createTime\":1471406871000,\"appointUserid\":0,\"publishId\":13,\"appointUserNickname\":\"\",\"userId\":10000,\"pictures\":\"\",\"userNickname\":\"MateralDesign\"},{\"id\":24,\"content\":\"吾虽浪迹天涯,却未泯失本心～\",\"createTime\":1471489658000," +
                "\"appointUserid\":0,\"publishId\":13,\"appointUserNickname\":\"\",\"userId\":10002,\"pictures\":\"\",\"userNickname\":\"十岁天才少年\"}],\"replyCount\":3,\"pictures\":\"Image/20180517/1471398852032.jpeg;Image/20180517/1471398852069.jpeg\",\"type\":\"0\",\"goodjobs\":[{\"id\":11,\"createTime\":1471406833000,\"publishId\":13,\"userId\":10000,\"userNickname\":\"MateralDesign\"}]," +
                "\"isvalid\":\"0\",\"content\":\"美美哒!\",\"id\":13,\"appointUserNickname\":\"\",\"nickName\":\"娇羞小萝莉\",\"address\":\"\",\"userId\":10013,\"longitude\":\"0\",\"latitude\":\"0\"},{\"appointUserid\":0,\"createTime\":1471398806000,\"icon\":\"Image/20180517/1471398965572.jpeg\",\"takeTimes\":\"14\",\"goodjobCount\":1,\"replys\":[],\"replyCount\":0,\"pictures\":\"Image/20180517/1471398798359.jpeg;Image/20180517/1471398798394.jpeg;Image/20180517/1471398798435.jpeg;Image/20180517/1471398799094.jpeg;Image/20180517/1471398800487.jpeg;Image/20180517/1471398800809.jpeg;Image/20180517/1471398801197.jpeg;Image/20180517/1471398801527.jpeg;Image/20180517/1471398801867.jpeg\",\"type\":\"0\",\"goodjobs\":[{\"id\":12,\"createTime\":1471406839000,\"publishId\":12,\"userId\":10000,\"userNickname\":\"MateralDesign\"}],\"isvalid\":\"0\",\"content\":\"万人追不如一人疼,万人宠不如一人懂。\",\"id\":12,\"appointUserNickname\":\"\",\"nickName\":\"雷菁\",\"address\":\"\",\"userId\":10013,\"longitude\":\"0\",\"latitude\":\"0\"},{\"appointUserid\":0,\"createTime\":1471394956000,\"icon\":\"\",\"takeTimes\":\"0\",\"goodjobCount\":1,\"replys\":[],\"replyCount\":0," +
                "\"pictures\":\"Image/20180517/1471394954041.jpeg\",\"type\":\"0\",\"goodjobs\":[{\"id\":10,\"createTime\":1471401148000,\"publishId\":11,\"userId\":10000,\"userNickname\":\"MateralDesign\"}],\"isvalid\":\"0\",\"content\":\"我是富婆,听大家说不想努力了,刚好找个小奶狗玩要年轻的.一个月3W8包吃住,大概一天三次吧,好了阿姨要说的就是这些.\",\"id\":11,\"appointUserNickname\":\"\",\"nickName\":\"carter\",\"address\":\"\",\"userId\":10102,\"longitude\":\"0\",\"latitude\":\"0\"},{\"appointUserid\":0,\"createTime\":1471233432000,\"icon\":\"\",\"takeTimes\":\"0\",\"goodjobCount\":2,\"replys\":[{\"id\":11,\"content\":\"秀\n" +
                "天秀\n" +
                "陈独秀\n" +
                "蒂花之秀\n" +
                "造化钟神秀\n" +
                "拼命三郎石秀\n" +
                "维多利亚内衣秀\n" +
                "吾何时能及汝之秀\n" +
                "同福客栈李秀莲的秀\n" +
                "我滴龟龟你怎么这么秀\n" +
                "一顾倾人城叹你眉清目秀\n" +
                "蓦然回首没想到你如此内秀\n" +
                "社会主义接班人就数你最优秀\n" +
                "无论多少言语表达不了你秀\n" +
                "腾讯旗下黄钻绿钻QQ秀\n" +
                "老太太都不服服你太秀\n" +
                "李云龙老婆秀芹的秀\n" +
                "求求你了不要再秀\n" +
                "米兰国际时装秀\n" +
                "湖南台真人秀\n" +
                "中国梦想秀\n" +
                "山清水秀\n" +
                "年代秀\n" +
                "刘秀\n" +
                "秀\",\"createTime\":1471233460000,\"appointUserid\":0,\"publishId\":9,\"appointUserNickname\":\"\",\"userId\":10102,\"pictures\":\"\",\"userNickname\":\"carter\"}],\"replyCount\":1,\"pictures\":\"Image/20160815/1471233430776.jpeg\",\"type\":\"0\"," +
                "\"goodjobs\":[{\"id\":7,\"createTime\":1471233446000,\"publishId\":9,\"userId\":10102,\"userNickname\":\"carter\"},{\"id\":15,\"createTime\":1472006199000,\"publishId\":9,\"userId\":10000,\"userNickname\":\"MateralDesign\"}],\"isvalid\":\"0\",\"content\":\"敢问公子姓甚名谁.家住何方家中可有妻儿.小女子不才想借公子一生说话.公子意下如何\",\"id\":9,\"appointUserNickname\":\"\",\"nickName\":\"carter\",\"address\":\"\",\"userId\":10102,\"longitude\":\"0\",\"latitude\":\"0\"},{\"appointUserid\":0,\"createTime\":1471229159000,\"icon\":\"Image/20160819/1471570856669.jpeg\",\"takeTimes\":\"12\",\"goodjobCount\":1,\"replys\":[],\"replyCount\":0,\"pictures\":\"Image/20160815/1471229143095.jpeg;Image/20160815/1471229143130.jpeg\",\"type\":\"0\",\"goodjobs\":[{\"id\":17,\"createTime\":1472006209000,\"publishId\":7,\"userId\":10000,\"userNickname\":\"MateralDesign\"}]," +
                "\"isvalid\":\"0\",\"content\":\"我相信我就是我,我相信明天.\",\"id\":7,\"appointUserNickname\":\"\",\"nickName\":\"MateralDesign\",\"address\":\"\",\"userId\":10000,\"longitude\":\"0\",\"latitude\":\"0\"},{\"appointUserid\":0,\"createTime\":1471227441000,\"icon\":\"Image/20160819/1471570856669.jpeg\",\"takeTimes\":\"12\",\"goodjobCount\":1,\"replys\":[],\"replyCount\":0,\"pictures\":\"Image/20160815/1471227434250.jpeg;Image/20160815/1471227434373.jpeg\",\"type\":\"0\",\"goodjobs\":[{\"id\":6,\"createTime\":1471227450000,\"publishId\":6,\"userId\":10000,\"userNickname\":\"MateralDesign\"}],\"isvalid\":\"0\",\"content\":\"梦想注定是孤独的旅行,路上少不了嘲笑和质疑\",\"id\":6,\"appointUserNickname\":\"\",\"nickName\":\"MateralDesign\",\"address\":\"\",\"userId\":10000,\"longitude\":\"0\",\"latitude\":\"0\"},{\"appointUserid\":0,\"createTime\":1471224271000,\"icon\":\"Image/20160819/1471570856669.jpeg\",\"takeTimes\":\"12\",\"goodjobCount\":1,\"replys\":[],\"replyCount\":0,\"pictures\":\"Image/20160815/1471224256630.jpg;Image/20160815/1471224256945.png\",\"type\":\"0\",\"goodjobs\":[{\"id\":14,\"createTime\":1471406854000,\"publishId\":4,\"userId\":10000,\"userNickname\":\"MateralDesign\"}],\"isvalid\":\"0\",\"content\":\"彼岸,梦里花开;伸手,咫尺天涯\",\"id\":4,\"appointUserNickname\":\"\",\"nickName\":\"MateralDesign\",\"address\":\"宝轩酒店\",\"userId\":10000,\"longitude\":\"113.2686712109507\",\"latitude\":\"23.123064640399328\"}]}}";
        return JSON.parseObject(str,Result.class);
    }

    /**
     * 图片
     */
    private static String[]Urls={"http://d.hiphotos.baidu.com/image/pic/item/e4dde71190ef76c6e453882a9f16fdfaaf516729.jpg", "http://h.hiphotos.baidu.com/image/pic/item/30adcbef76094b36db47d2e4a1cc7cd98c109de6.jpg","http://g.hiphotos.baidu.com/image/pic/item/0d338744ebf81a4c27dc0dcdd52a6059242da6cc.jpg"
            ,"http://c.hiphotos.baidu.com/image/h%3D200/sign=d21f63f99d3df8dcb93d8891fd1072bf/78310a55b319ebc415951b978026cffc1f1716ca.jpg","http://d.hiphotos.baidu.com/image/pic/item/54fbb2fb43166d22dc28839a442309f79052d265.jpg"
    ,"http://c.hiphotos.baidu.com/image/pic/item/03087bf40ad162d9d0e7560313dfa9ec8a13cda7.jpg","http://g.hiphotos.baidu.com/image/h%3D200/sign=16f4ef3e35adcbef1e3479069cae2e0e/6d81800a19d8bc3e7763d030868ba61ea9d345e5.jpg"
    ,"http://g.hiphotos.baidu.com/image/pic/item/8d5494eef01f3a29a3b0e6c49b25bc315c607cbb.jpg","http://c.hiphotos.baidu.com/image/h%3D200/sign=548da2d73f6d55fbdac671265d224f40/a044ad345982b2b7a2b8f7cd33adcbef76099b90.jpg"
    ,"http://g.hiphotos.baidu.com/image/pic/item/7acb0a46f21fbe09359315d16f600c338644ad22.jpg","http://h.hiphotos.baidu.com/image/h%3D200/sign=9d4948d52c738bd4db21b531918a876c/6a600c338744ebf85db15337dbf9d72a6159a7f1.jpg"
    ,"http://e.hiphotos.baidu.com/image/h%3D200/sign=7683f02abc096b639e1959503c328733/203fb80e7bec54e74a142d1bbb389b504fc26a3e.jpg"};

    /**
     * 获取随机图片串
     * @param num
     * @return
     */
    public static String getRandomPhotoUrlString(int num) {
        StringBuilder sdbResult = new StringBuilder();
        if(num>0) {
            String[] photoUrls = new String[num>9?9:num];
            for (int i = 0; i< num ; i++) {
                if ( sdbResult.length() > 0 )
                {
                    sdbResult.append( ";" ).append( Urls[new Random().nextInt(Urls.length)] );
                }else{
                    sdbResult.append( Urls[new Random().nextInt(Urls.length)] );
                }

            }
        }
        return sdbResult.toString();
    }
    /**
     * 获取随机图片串
     * @return
     */
    public static String getRandomPhotoUrl() {
        return  Urls[new Random().nextInt(Urls.length)];
    }
}
