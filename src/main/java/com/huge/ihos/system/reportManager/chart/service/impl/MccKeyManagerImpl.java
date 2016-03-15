package com.huge.ihos.system.reportManager.chart.service.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.huge.ihos.system.reportManager.chart.dao.MccKeyDao;
import com.huge.ihos.system.reportManager.chart.dao.MccKeyDetailDao;
import com.huge.ihos.system.reportManager.chart.model.IntegrationData;
import com.huge.ihos.system.reportManager.chart.model.MccKey;
import com.huge.ihos.system.reportManager.chart.model.MccKeyDetail;
import com.huge.ihos.system.reportManager.chart.service.MccKeyManager;
import com.huge.ihos.system.reportManager.chart.util.ChartUtil;
import com.huge.ihos.system.reportManager.search.util.FontUtil;
import com.huge.ihos.system.reportManager.search.util.GraphicsUtil;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.util.OtherUtil;
import com.huge.webapp.pagers.JQueryPager;

@Service( "mccKeyManager" )
public class MccKeyManagerImpl
    extends GenericManagerImpl<MccKey, String>
    implements MccKeyManager {
    MccKeyDao mccKeyDao;

    MccKeyDetailDao mccKeyDetailDao;

    public MccKeyDetailDao getMccKeyDetailDao() {
        return mccKeyDetailDao;
    }

    @Autowired
    public void setMccKeyDetailDao( MccKeyDetailDao mccKeyDetailDao ) {
        this.mccKeyDetailDao = mccKeyDetailDao;
    }

    @Autowired
    public MccKeyManagerImpl( MccKeyDao mccKeyDao ) {
        super( mccKeyDao );
        this.mccKeyDao = mccKeyDao;
    }

    public JQueryPager getMccKeyCriteria( JQueryPager paginatedList ) {
        return mccKeyDao.getMccKeyCriteria( paginatedList );
    }

    public MccKey getCkey( String cKey ) {
        return mccKeyDao.getCkey( cKey );
    }

    public void saveMccKey( MccKey mccKey ) {
        if ( mccKey.getChartType().equals( "表盘" ) ) {
            if ( OtherUtil.measureNotNull( mccKey.getGaugeOuterRadius() ) ) {
                Integer radius = Integer.parseInt( mccKey.getGaugeOuterRadius() );
                mccKey.setRadius( radius.toString() );//表针半径
                mccKey.setBaseWidth( ( radius / 10 ) + "" );//表针宽度
                mccKey.setDisplayValueDistance( ( radius / 9 ) + "" );// 值与表盘外围距离 
                mccKey.setPivotRadius( ( radius / 15 ) + "" );//表针根部圆心半径
                //mccKey.setGaugeOriginX("350");	    			
                mccKey.setGaugeOriginY( ( radius + 2 * ( radius / 9 ) ) + "" );
            }
            else
                return;
        }
        mccKeyDao.save( mccKey );
    }

    public Object[] choiceChart( MccKey mccKey, Map<String,String> conditionMap )
        throws SQLException {
        String chartType = mccKey.getChartType();
        String mySql = mccKey.getMysql();
        Object[] fanHui = new Object[1];
        if ( OtherUtil.measureNotNull( mySql )) {
            String sql = ChartUtil.joinSql( conditionMap );
            mySql = mySql.replace( "%s", sql );
            //System.out.println("choiceChartSql:" + mySql);
            Map<String, Object[]> map = mccKeyDao.obtainDataMethod( mySql );
            if ( chartType.equals( "表盘" ) ) return biaoPan( mccKey, mySql );
            
            else if ( chartType.equals( "饼图" ) ) return bing( map, mccKey );
            
            else if ( chartType.equals( "柱形图" ) ) return zhuXingB( map, mccKey );
            
            else if ( chartType.equals( "折线图" ) ) return zheXianB( map, mccKey );
            
            else if( chartType.equals( "雷达图" ) ) return RadarChar( map, mccKey );
            
            else return fanHui;
        }
        else {
            fanHui[0] = "没有可执行的Sql语句!!!";
            return fanHui;
        }

    }

    public boolean validataMccKeyId( String mccKeyId ) {
        mccKeyDao.existCode( "T_MccKey", "mccKeyId", mccKeyId, "add" );
        return false;
    }

    @SuppressWarnings("unchecked")
	public Object[] bing( Map<String, Object[]> map, MccKey mccKey ) {
        StringBuilder bingTuStr = new StringBuilder();
        Object[] fanHui = new Object[2];
        List<IntegrationData> listIntegration = null;


        //得到的拼接字符串
        String ms = ChartUtil.mosaicBingStr( mccKey );
        //主显字段
        String keyField = mccKey.getKeyField();

        //坎值
        double yKanZhi = mccKey.getKanZhi();
        //
        String color = mccKey.getColor();
        double cZhi = mccKey.getSheDingZhi();
        String[] keyFileds = keyField.split( "," );
        if ( keyFileds.length == 3 ) {
            Object[] listTitle = map.get( keyFileds[0] );
            Object[] listValue = map.get( keyFileds[1] );
            Object[] listPercent = map.get( keyFileds[2] );
            //整合列表显示数据
            listIntegration = ChartUtil.IntegrationData( listTitle, listValue, listPercent );

            double kanZhi = ChartUtil.calcOther( listValue, yKanZhi );
            if ( listTitle.length > 0 && listValue.length > 0 && listTitle.length == listValue.length ) {
                bingTuStr .append("<?xml version='1.0' encoding='UTF-16'?><chart " + ms + ">");
                //所有小于坎值相加之和
                double vk = 0;
                for ( int i = 0; i < listTitle.length; i++ ) {
                    double v = 0;
                    if ( OtherUtil.measureNotNull( listValue[i] ) ) {
                        v = Double.parseDouble( listValue[i].toString() );
                        //判断是否需要显示数值(在显示百分比的情况下)
                        if ( mccKey.isDisShowValue() ) {
                            if ( v > kanZhi && kanZhi > 0 ) {
                                bingTuStr .append( "<set label='" + listTitle[i] + OtherUtil.numberBigDecimal( listValue[i] ) + "' value='" + v + "'");
                            }
                            else if ( v <= kanZhi && kanZhi > 0 ) {
                                vk += v;
                                continue;
                            }
                            else {
                                bingTuStr .append( "<set label='" + listTitle[i] + OtherUtil.numberBigDecimal( listValue[i] ) + "' value='" + v + "'");
                            }
                        }
                        else {
                            if ( v > kanZhi && kanZhi > 0 ) {
                                bingTuStr .append( "<set label='" + listTitle[i] + "' value='" + OtherUtil.formatBigDecimal( v ) + "'");
                            }
                            else if ( v <= kanZhi && kanZhi > 0 ) {
                                vk += v;
                                continue;
                            }
                            else {
                                bingTuStr .append( "<set label='" + listTitle[i] + "' value='" + OtherUtil.formatBigDecimal( v ) + "'");
                            }
                        }
                        if ( OtherUtil.measureNotNull( color ) ) {
                            if ( v >= cZhi && cZhi > 0 ) {
                                bingTuStr .append( " color='" + color + "'");
                            }
                        }
                    }
                    bingTuStr .append( "/>");
                }
                if ( mccKey.isDisShowValue() ) {
                    if ( vk != 0 )
                        bingTuStr .append( "<set label='其他" + OtherUtil.numberBigDecimal( vk ) + "'  value='" + vk + "'/>");
                }
                else {
                    if ( vk != 0 )
                        bingTuStr .append( "<set label='其他'  value='" + OtherUtil.formatBigDecimal( vk ) + "'/>");
                }
                bingTuStr .append( "</chart>");
            }
            else {
                fanHui[0] = "所给数据不符合饼图规范!!!";
                return fanHui;
            }
        }
        else {
            fanHui[0] = "所给数据不符合饼图规范!!!";
            return fanHui;
        }
        fanHui[0] = bingTuStr.toString();
        fanHui[1] = listIntegration;
        return fanHui;
    }

    @SuppressWarnings( { "unchecked", "rawtypes" } )
    public Object[] RadarChar( Map<String, Object[]> map ,MccKey mccKey){
    	Object[] fanHui = new Object[1];
    	String radarStr="";
    	
        Object[] myData = ChartUtil.dataArrangement( map, mccKey );
    	  //最外层坐标
        List listXs = (List) myData[0];
        Map<Object, Map<Object, Object>> mapVs = (Map<Object, Map<Object, Object>>) myData[1];
        //标题
        List listTs = (List) myData[2];
        //格式化数据
       // String pattern = mccKey.getDecimalPrecision();
        //得到的拼接字符串
        String ms = ChartUtil.masaicRadarStr( mccKey );
        //主显字段
        String keyField = mccKey.getKeyField();
        String[] keyFileds = keyField.split( "," );
        if ( keyFileds.length >= 3 ) {
        	//画出最外层坐标坐标
            if ( listTs.size() > 0 ) {
            	radarStr = "<?xml version='1.0' encoding='UTF-16'?><chart  " + ms + ">";
            	radarStr += "<categories font='"+FontUtil.GetSystemFontOne()+"' fontSize='13'>";
                for ( int i = 0; i < listXs.size(); i++ ) {
                	radarStr += "<category  label= '" + listXs.get( i ) + "' />";
                }
                
                radarStr += "</categories>";
                //预定义的默认折线颜色
                String[] y = { "1D8BD1", "F1683C", "2AD62A", "DBDC25", "66FF99", "CC00FF", "000066", "33FFCC", "99FF00", "0000FF" };
                for ( int i = 0; i < listTs.size(); i++ ) {
                    Map listVs = mapVs.get( listTs.get( i ) );
                    radarStr +=
                        "<dataset seriesName='" + listTs.get( i ) + "' color='" + y[i] + "' anchorBorderColor='" + y[i] + "' anchorBgColor='" + y[i]
                            + "' >";
                    for ( int j = 0; j < listVs.size(); j++ ) {

                    	radarStr += "<set value='" + listVs.get( listXs.get( j ) ) + "' />";

                    }
                    radarStr += "</dataset>";
                }
                radarStr +=
                    "<styles> <definition> <style name='CaptionFont' type='font' size='12' /> </definition> <application> <apply toObject='CAPTION' styles='CaptionFont' /> <apply toObject='SUBCAPTION' styles='CaptionFont' /> </application> </styles> </chart>";
                
            }
        }else {
            fanHui[0] = "所给数据不符合雷达图规范!!!";
            return fanHui;
        }
        fanHui[0] = radarStr;
        
        
    	return fanHui;
    }
    

    @SuppressWarnings( { "unchecked", "rawtypes" } )
    public Object[] zheXianB( Map<String, Object[]> map, MccKey mccKey ) {
        Object[] fanHui = new Object[1];
        String zheXianStr = "";

        Object[] myData = ChartUtil.dataArrangement( map, mccKey );
        //X轴坐标
        List listXs = (List) myData[0];
        Map<Object, Map<Object, Object>> mapVs = (Map<Object, Map<Object, Object>>) myData[1];
        //标题(图形最下方)
        List listTs = (List) myData[2];
        //格式化数据
        //String pattern = mccKey.getDecimalPrecision();
        //得到的拼接字符串
        String ms = ChartUtil.mosaicStr( mccKey );
        //主显字段
        String keyField = mccKey.getKeyField();
        String[] keyFileds = keyField.split( "," );
        if ( keyFileds.length >= 3 ) {

            //画出X轴坐标
            if ( listTs.size() > 0 ) {
                zheXianStr = "<?xml version='1.0' encoding='UTF-16'?><chart  " + ms + ">";
                zheXianStr += "<categories>";
                for ( int i = 0; i < listXs.size(); i++ ) {
                    zheXianStr += "<category  label= '" + listXs.get( i ) + "' />";
                }
                zheXianStr += "</categories>";
                //预定义的默认折线颜色
                String[] y = { "1D8BD1", "F1683C", "2AD62A", "DBDC25", "66FF99", "CC00FF", "000066", "33FFCC", "99FF00", "0000FF" };
                for ( int i = 0; i < listTs.size(); i++ ) {
                    Map listVs = mapVs.get( listTs.get( i ) );
                    zheXianStr +=
                            "<dataset seriesName='" + listTs.get( i ) + "' color='" + y[i] + "' anchorBorderColor='" + y[i] + "' anchorBgColor='" + y[i]
                                + "' >";
                    for ( int j = 0; j < listVs.size(); j++ ) {

                        zheXianStr += "<set value='" + listVs.get( listXs.get( j ) ) + "' />";

                    }
                    zheXianStr += "</dataset>";
                }
                zheXianStr +=
                    "<styles> <definition> <style name='CaptionFont' type='font' size='12' /> </definition> <application> <apply toObject='CAPTION' styles='CaptionFont' /> <apply toObject='SUBCAPTION' styles='CaptionFont' /> </application> </styles> </chart>";
                //System.out.println(zheXianStr);

            }
        }
        else {
            fanHui[0] = "所给数据不符合折线图规范!!!";
            return fanHui;
        }
        fanHui[0] = zheXianStr;
        return fanHui;
    }

    @SuppressWarnings( { "unchecked", "rawtypes" } )
    public String zheXianSearch( List list , String searchTitle,LinkedHashSet<String> keyName) {
        String zheXianStr = "";
        Object[] myData = ChartUtil.dataArrangement( list );
        String depptName = myData[3].toString();
        
        //X轴坐标
        TreeSet<Integer> listXs = (TreeSet) myData[0];
        //Map<Object, Map<Object, Object>> mapVs = (Map<Object, Map<Object, Object>>) myData[1];
        HashMap<String, String> mapVs = (HashMap<String, String>)myData[1];
        //标题(图形最下方)
        LinkedHashSet<String> listTs= (LinkedHashSet) myData[2];
        if(keyName!=null&&listTs.size()==keyName.size()){
        	listTs = keyName;
        }
        if ( listTs.size() > 0 ) {
            zheXianStr =
                "<?xml version='1.0' encoding='UTF-16'?><chart  caption='"+depptName+" 指标趋势分析' showValues='0' chartLeftMargin='30' chartRightMargin='50' baseFont='黑体' baseFontSize='12' zxZhiBaseFontSize='8' bgAlpha='10,10' outCnvbaseFontSize='12' lineThickness='2' anchorRadius='3' divLineAlpha='100' divLineColor='CC3300' numvdivlines='8' showAlternateHGridColor='1' yAxisNameWidth='20'>";
            zheXianStr += "<categories>";
            Iterator<Integer> xsIt = listXs.iterator();
            while (xsIt.hasNext()) {
            	Integer cpr = xsIt.next();
            	zheXianStr += "<category  label= '" + cpr+ "' />";
			}
           /* for ( int i = 0; i < xsIt.size(); i++ ) {
                zheXianStr += "<category  label= '" + listXs.iterator()+ "' />";
            }*/
            zheXianStr += "</categories>";
            //预定义的默认折线颜色
            String[] y = { "1D8BD1", "F1683C", "2AD62A", "DBDC25", "66FF99", "CC00FF", "000066", "33FFCC", "99FF00", "0000FF" };
            Iterator<String> tsIt = listTs.iterator();
            int i = 0;
            while (tsIt.hasNext()) {
            	String kn = tsIt.next();
            	zheXianStr +=
                    "<dataset seriesName='" + kn + "' color='" + y[i] + "' anchorBorderColor='" + y[i] + "' anchorBgColor='" + y[i]
                        + "' >";
            	xsIt = listXs.iterator();
            	while (xsIt.hasNext()) {
            		Integer cp = xsIt.next();
            		zheXianStr += "<set value='" + (mapVs.get(kn+cp)==null?0.0:Double.parseDouble(mapVs.get(kn+cp).toString().trim())) + "' />";
            	}
            	zheXianStr += "</dataset>";
            	i++;
            }
            /*for ( int i = 0; i < listTs.size(); i++ ) {
                Map listVs = mapVs.get( listTs.get( i ) );
                zheXianStr +=
                    "<dataset seriesName='" + listTs.get( i ) + "' color='" + y[i] + "' anchorBorderColor='" + y[i] + "' anchorBgColor='" + y[i]
                        + "' >";
                for ( int j = 0; j < listVs.size(); j++ ) {

                    zheXianStr += "<set value='" + listVs.get( listXs.get( j ) ) + "' />";

                }
                zheXianStr += "</dataset>";
            }*/
            zheXianStr +=
                "<styles> <definition> <style name='CaptionFont' type='font' size='12' /> </definition> <application> <apply toObject='CAPTION' styles='CaptionFont' /> <apply toObject='SUBCAPTION' styles='CaptionFont' /> </application> </styles> </chart>";
        }
        return zheXianStr;
    }

    @SuppressWarnings( { "unchecked", "rawtypes" } )
    public Object[] zhuXingB( Map<String, Object[]> map, MccKey mccKey ) {
        Object[] fanHui = new Object[1];
        String zhuZhuangStr = "";
        Object[] myData = ChartUtil.dataArrangement( map, mccKey );
        //X轴坐标
        List listXs = (List) myData[0];
        Map<Object, Map<Object, Object>> mapVs = (Map<Object, Map<Object, Object>>) myData[1];
        //标题(图形最下方)
        List listTs = (List) myData[2];
        //格式化数据
//        String pattern = mccKey.getDecimalPrecision();
        //得到的拼接字符串
        String ms = ChartUtil.mosaicStr( mccKey );
        //主显字段
        String keyField = mccKey.getKeyField();
        String[] keyFileds = keyField.split( "," );
        if ( keyFileds.length >= 3 ) {

            //Set<Object> listTitle=mapVs.keySet();
            //X画线的值
            //Object[] listX=mapX.get(keyFileds[1]);

            //Object[] listValue=mapValue.get(keyFileds[3]);

            //画出X轴坐标
            zhuZhuangStr += "<?xml version='1.0' encoding='UTF-16'?><chart " + ms ;
            if(listTs.size()==1){
            	zhuZhuangStr += " showLegend='0'";
            }
            zhuZhuangStr += "><categories>";
            for ( int i = 0; i < listXs.size(); i++ ) {

                zhuZhuangStr += "<category label= '" + listXs.get( i ) + "' />";

            }

            zhuZhuangStr += "</categories>";

            zhuZhuangStr += "<trendlines><line  color='000033' thickness='1' alpha='60' showOnTop='1' isTrendZone='0'/></trendlines>";

            //预定义的默认折线颜色
            String[] y = { "AFD8F8", "F6BD0F", "8BBA00", "DBDC25", "66FF99", "CC00FF", "000066", "33FFCC", "99FF00", "0000FF","1D8BD1","F1683C","2AD62A"};
            String[] yy = {"1D8BD1","F1683C","2AD62A","DBDC25","66FF99","CC00FF", "000066", "33FFCC", "99FF00", "0000FF","AFD8F8","F6BD0F","8BBA00"};
            for ( int i = 0; i < listTs.size(); i++ ) {
                Map listVs = mapVs.get( listTs.get( i ) );
                	zhuZhuangStr += "<dataset seriesName='" + listTs.get( i ) +"' color='" + y[i]+"' >";
                for ( int j = 0; j < listVs.size(); j++ ) {
                	zhuZhuangStr += "<set value='" + listVs.get( listXs.get( j ) ) +"'";
                	if(listTs.size()==1){
                		zhuZhuangStr += " color='" + yy[j] +"'";
                	}
                	zhuZhuangStr += " />";
                }
                zhuZhuangStr += "</dataset>";
            }

            zhuZhuangStr += "</chart>";

        }
        fanHui[0] = zhuZhuangStr;
        return fanHui;
    }

    @Transactional( readOnly = true )
    @SuppressWarnings( { "unchecked" } )
    public Object[] biaoPan( MccKey mccKey, String mySql )
        throws SQLException {
        String[] data = { mccKey.getMccKeyId(), mySql, mccKey.getKeyField() };
        Object[] myData = mccKeyDetailDao.clockDialMethod( data );
        Object[] fanZhi = new Object[4];
        String biaoPanStr = "";
        if ( myData.length != 2 ) {
            fanZhi[0] = "所给数据不符合规范";
            return fanZhi;
        }
        //String[] y={"399E38","","E48739","B41527","399E38","B41527"};
        List<MccKeyDetail> list = (List<MccKeyDetail>) myData[0];

        MccKeyDetail mccKeyDetail = new MccKeyDetail();
        Collections.sort( list, mccKeyDetail );

        BigDecimal maxValue = BigDecimal.valueOf( 0 );
        boolean warning = false;

        Object[] colors = new Object[list.size()];
        for ( int i = 0; i < list.size(); i++ )
            colors[i] = list.get( i ).getColor();
        for ( int i = 0; i < list.size(); i++ ) {
            if ( list.get( i ).getWarning() ) {
                maxValue = list.get( i ).getMinValue();
                warning = true;
            }
        }
        String[] ms = ChartUtil.biaoPanStr( mccKey );
        String ms1 = ms[0];
        String ms2 = ms[1];
        BigDecimal value = (BigDecimal) myData[1];
//        double s = 0;
//        BigDecimal t = BigDecimal.valueOf( 0 );
        BigDecimal CurrentValue = value;
        if ( OtherUtil.measureNull( value )) {
            fanZhi[0] = "";
            return fanZhi;
        }
        biaoPanStr +=
            "<?xml version='1.0' encoding='gbk'?><Chart  fillAngle='45' "
                + ms1
                + " upperLimit='"
                + list.get( list.size() - 1 ).getMaxValue()
                + "'  majorTMNumber='10' majorTMHeight='10' showGaugeBorder='1'  ecimalPrecision='2'    pivotBorderThickness='5' pivotFillMix='ffffff,330000'><colorRange>";
        for ( int i = 0; i < list.size(); i++ ) {
            biaoPanStr +=
                "<color minValue='" + list.get( i ).getMinValue() + "' maxValue='" + list.get( i ).getMaxValue() + "' code='" + colors[i] + "'/>";
        }

        biaoPanStr += "</colorRange><dials><dial value='" + value + "' borderAlpha='0' bgColor='330000'  topWidth='1' " + ms2 + "/></dials></Chart>";
        fanZhi[0] = biaoPanStr;
        fanZhi[1] = maxValue;
        fanZhi[2] = CurrentValue;
        fanZhi[3] = warning;
        return fanZhi;
    }

    /**
     * 本量利
     */
    public String graDisPlay( String gdParam, String biaoTi, String dataMsg, String fileName, String picName, String xyName, String biaoZhu )
        throws SQLException {
        String str = "";

        gdParam = gdParam.toUpperCase();
        dataMsg = dataMsg.toUpperCase();
        biaoTi = biaoTi.toUpperCase();

        String[] params = gdParam.split( "," );
        if ( params.length != 5 )
            return str = "数据库URL长度不足，请检查";
        String[] biaoTiParams = biaoTi.split( "," );
        String[] dataParamsJ = dataMsg.split( "-" );
        String[] dataParams = new String[dataParamsJ.length];
        for (int i = 0; i < dataParamsJ.length; i++) {
        	String data=dataParamsJ[i];
        	if(data.contains(",")){
        		data=data.replace(",", "");
			}
        	dataParams[i]=data;
		}
        double[] obj = new double[5];
        String[] liShi = new String[2];
        String[] biaoTou = new String[3];
        for ( int i = 0; i < params.length; i++ ) {
            for ( int j = 0; j < dataParams.length; j++ ) {
                liShi = dataParams[j].split( ":" );
                if ( dataParams[j].contains( params[i] ) ) {
                    obj[i] = Double.parseDouble(liShi[1] );
                }
                if ( dataParams[j].contains( biaoTiParams[0] ) || dataParams[j].contains( biaoTiParams[2] ) ) {
                    if ( dataParams[j].contains( biaoTiParams[1] ) ) {
                        biaoTou[1] = liShi[1];
                    }
                    if ( dataParams[j].contains( biaoTiParams[2] ) ) {
                        biaoTou[2] = liShi[1];
                    }
                    if ( !dataParams[j].contains( biaoTiParams[1] ) && dataParams[j].contains( biaoTiParams[0] ) ) {
                        biaoTou[0] = liShi[1];
                    }
                }

            }
        }
        if ( OtherUtil.measureNotNull( biaoTou[0] ) && OtherUtil.measureNotNull( biaoTou[1] ) && OtherUtil.measureNotNull( biaoTou[2] ) )
            picName = biaoTou[2] + biaoTou[0] + "--" + biaoTou[1] + picName;
        if ( OtherUtil.measureNull( biaoTou[1] ) && OtherUtil.measureNotNull( biaoTou[2] ) && OtherUtil.measureNotNull( biaoTou[0] ) )
            picName = biaoTou[2] + biaoTou[0] + "--" + biaoTou[0] + picName;

        String[] xyNameS = xyName.split( "," );
        String[] biaoZhuS = biaoZhu.split( "," );
        if ( obj[1] > obj[0] ) {
            return str = "保本点不存在！";
        }

        GraphicsUtil.paintComponent( obj[0], obj[1], obj[2], (int)obj[3], obj[4], fileName, picName, xyNameS, biaoZhuS );

        return str;

    }
}