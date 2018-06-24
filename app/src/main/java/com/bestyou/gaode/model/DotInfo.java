package com.bestyou.gaode.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aaron on 2016/10/17.
 */

public class DotInfo {

    private String dotId;
    private double dotLat;
    private double dotLon;


    public String getDotId() {
        return dotId;
    }

    public void setDotId(String dotId) {
        this.dotId = dotId;
    }

    public double getDotLat() {
        return dotLat;
    }

    public void setDotLat(double dotLat) {
        this.dotLat = dotLat;
    }

    public double getDotLon() {
        return dotLon;
    }

    public void setDotLon(double dotLon) {
        this.dotLon = dotLon;
    }

    /**
     * 初始化数据
     *
     * 摇光
     35.321542，79.241253

     开阳
     41.851333, 87.036707

     玉衡
     敦煌市
     40.845863, 94.353507

     天权
     阿拉善左旗
     中国内蒙古自治区阿拉善盟
     40.571146, 104.975471

     天玑
     铜川市
     中国陕西省
     35.034814, 108.866098

     天璇
     威海市
     37.316538, 122.119367

     天枢
     通榆县
     44.803825, 122.619437
     *
     * @return
     */
    public static List<DotInfo> initData() {
        List<DotInfo> dotInfoList = new ArrayList<>();

        DotInfo dotInfo1 = new DotInfo();
        dotInfo1.setDotLat(35.321542);
        dotInfo1.setDotLon(79.241253);
        dotInfo1.setDotId("dotInfo1");
        dotInfoList.add(dotInfo1);

        DotInfo dotInfo2 = new DotInfo();
        dotInfo2.setDotLat(41.851333);
        dotInfo2.setDotLon(87.036707);
        dotInfo2.setDotId("dotInfo2");
        dotInfoList.add(dotInfo2);

        DotInfo dotInfo3 = new DotInfo();
        dotInfo3.setDotLat(40.845863);
        dotInfo3.setDotLon(94.353507);
        dotInfo3.setDotId("dotInfo3");
        dotInfoList.add(dotInfo3);

        DotInfo dotInfo4 = new DotInfo();
        dotInfo4.setDotLat(40.571146);
        dotInfo4.setDotLon(104.975471);
        dotInfo4.setDotId("dotInfo4");
        dotInfoList.add(dotInfo4);

        DotInfo dotInfo5 = new DotInfo();
        dotInfo5.setDotLat(35.034814);
        dotInfo5.setDotLon(108.866098);
        dotInfo5.setDotId("dotInfo5");
        dotInfoList.add(dotInfo5);

        DotInfo dotInfo6 = new DotInfo();
        dotInfo6.setDotLat(37.316538);
        dotInfo6.setDotLon(122.119367);
        dotInfo6.setDotId("dotInfo6");
        dotInfoList.add(dotInfo6);

        DotInfo dotInfo7 = new DotInfo();
        dotInfo7.setDotLat(44.803825);
        dotInfo7.setDotLon(122.619437);
        dotInfo7.setDotId("dotInfo7");
        dotInfoList.add(dotInfo7);

//        DotInfo dotInfo8 = new DotInfo();
//        dotInfo8.setDotLat(39.980959);
//        dotInfo8.setDotLon(116.331772);
//        dotInfo8.setDotId("dotInfo8");
//        dotInfoList.add(dotInfo8);
//
//        DotInfo dotInfo9 = new DotInfo();
//        dotInfo9.setDotLat(39.925659);
//        dotInfo9.setDotLon(116.508567);
//        dotInfo9.setDotId("dotInfo9");
//        dotInfoList.add(dotInfo9);
//
//        DotInfo dotInfo10 = new DotInfo();
//        dotInfo10.setDotLat(39.88543);
//        dotInfo10.setDotLon(116.461778);
//        dotInfo10.setDotId("dotInfo10");
//        dotInfoList.add(dotInfo10);
//
//        DotInfo dotInfo11 = new DotInfo();
//        dotInfo11.setDotLat(39.98343);
//        dotInfo11.setDotLon(116.311843);
//        dotInfo11.setDotId("dotInfo11");
//        dotInfoList.add(dotInfo11);
//
//        DotInfo dotInfo12 = new DotInfo();
//        dotInfo12.setDotLat(40.029849);
//        dotInfo12.setDotLon(116.313302);
//        dotInfo12.setDotId("dotInfo12");
//        dotInfoList.add(dotInfo12);

        return dotInfoList;
    }
}
