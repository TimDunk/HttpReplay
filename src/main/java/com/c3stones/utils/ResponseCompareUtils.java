package com.c3stones.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.flipkart.zjsonpatch.DiffFlags;
import com.flipkart.zjsonpatch.JsonDiff;

import java.util.*;
import java.util.function.Predicate;

public class ResponseCompareUtils {
    public static void main(String[] args){
        String json1="{\"result\":{\"recordCount\":1,\"result\":[{\"AuthCode\":\"\",\"CADUrl\":\"\",\"CategoryName\":\"电视柜\",\"DoorTypeSetting\":0,\"LeafType\":\"\",\"MaterialList\":[],\"Model\":null,\"OpenDirection\":\"\",\"Orientation\":\"0\",\"ParentMaterialIdList\":null,\"RelationList\":[],\"TagIdList\":[],\"ThumbPath\":null,\"UploadFiles\":null,\"channelIsOut\":0,\"designMaterialTagIds\":\"19650,18418,873580,873422\",\"isCollect\":0,\"isGlb\":0,\"isLock\":0,\"isOut\":0,\"isQuality\":0,\"quoteCode\":\"ClobField/DesignMaterial/quoteCode/20220418/FYD7EGZKzb1650250548535.txt\",\"remark\":\"测试备注\",\"salePrice\":0.0,\"versionTime\":\"2022-04-18 10:55:50\",\"WCCId\":\"\",\"ModifyUserId\":\"1100241524\",\"ModifyTime\":\"2022-04-18 10:55:50\",\"Color\":\"\",\"IShasProduct\":0,\"FreeCategoryId\":106699719,\"SubParts\":\"\",\"WidthMax\":0.0,\"WidthMin\":0.0,\"WidthDefault\":\"\",\"DepthMax\":0.0,\"DepthMin\":0.0,\"DepthDefault\":\"\",\"HeightMax\":0.0,\"HeightMin\":0.0,\"HeightDefault\":\"\",\"SortNumber\":0,\"RawMaterialType\":\"\",\"UseArea\":\"\",\"UseCategory\":\"\",\"UseStage\":\"\",\"A3dSource\":\"/UpFile/C00002400/PMC/DesignMaterial/202204/154152227/VR154152227.svj?version=1649304078849\",\"IsTop\":0,\"IsRelate\":0,\"IsUnit\":0,\"ReplaceImg\":\"1\",\"LightProperty\":\"\",\"MaterialType\":\"Furniture\",\"MaterialCategoryName\":\"\",\"DesignCode\":\"CP020099\",\"MaterialId\":\"154152227\",\"PartsName\":\"\",\"Parts\":\"\",\"Is3D\":1,\"MaterialName\":\"诺岩电视柜\",\"ProductId\":\"51477435\",\"PICSize\":\"1784.96X375.54X395.39\",\"PICLength\":1784.96,\"PICWidth\":375.54,\"PICHeight\":395.39,\"ModelDepth\":0,\"CategoryId\":106699719,\"OrganId\":\"C00002400\",\"DeptId\":\"00388632\",\"Author\":\"1000317743\",\"PostTime\":\"2022-04-07 11:31:47\",\"IsDelete\":0,\"ImagePath\":\"/UpFile/C00002400/DesignMaterial/202204/07/154152227/VRenderImage_1738933210.png\",\"ExpandAttrib\":\"\",\"IsShare\":0,\"IsPublic\":0,\"ExtendField\":\"\",\"ModelFlag\":1,\"CostPrice\":0.0,\"WholesalePrice\":0.0,\"RetailPrice\":0.0,\"IsRender\":0,\"PlaceRule\":\"stickFloor\",\"Property\":\"请选择\",\"HoleLength\":0,\"HoleWidth\":0,\"PlaceHeight\":\"\",\"Content\":\"\",\"IsA3d\":\"1\",\"XmlContent\":\"\",\"MaterialCategory\":\"\",\"ContentCloudUrl\":null,\"ContentCDNUrl\":null,\"XmlContentCloudUrl\":null,\"XmlContentCDNUrl\":null,\"SpecialTexture\":\"\",\"MixPaveSetting\":\"\",\"MaterialAttribute\":\"\",\"BelongType\":\"2\",\"Version\":0,\"BrickId\":\"\"}]},\"success\":true}";
        String json2="{\"result\":{\"recordCount\":1,\"result\":[{\"AuthCode\":\"\",\"CADUrl\":\"\",\"CategoryName\":\"电视柜\",\"DoorTypeSetting\":0,\"LeafType\":\"\",\"MaterialList\":[],\"Model\":null,\"OpenDirection\":\"\",\"Orientation\":\"0\",\"ParentMaterialIdList\":null,\"RelationList\":[],\"TagIdList\":[],\"ThumbPath\":null,\"UploadFiles\":null,\"channelIsOut\":0,\"designMaterialTagIds\":\"19650,18418,873580,873422\",\"isCollect\":0,\"isGlb\":0,\"isLock\":0,\"isOut\":0,\"isQuality\":0,\"quoteCode\":\"ClobField/DesignMaterial/quoteCode/20220418/FYD7EGZKzb1650250548535.txt\",\"remark\":\"测试备注\",\"salePrice\":0.0,\"versionTime\":\"2022-04-18 10:55:50\",\"WCCId\":\"\",\"ModifyUserId\":\"1100241524\",\"ModifyTime\":\"2022-04-1810:55:50\",\"Color\":\"\",\"IShasProduct\":0,\"FreeCategoryId\":106699719,\"SubParts\":\"\",\"WidthMax\":0.0,\"WidthMin\":0.0,\"WidthDefault\":\"\",\"DepthMax\":0.0,\"DepthMin\":0.0,\"DepthDefault\":\"\",\"HeightMax\":0.0,\"HeightMin\":0.0,\"HeightDefault\":\"\",\"SortNumber\":0,\"RawMaterialType\":\"\",\"UseArea\":\"\",\"UseCategory\":\"\",\"UseStage\":\"\",\"A3dSource\":\"/UpFile/C00002400/PMC/DesignMaterial/202204/154152227/VR154152227.svj?version=1649304078849\",\"IsTop\":0,\"IsRelate\":0,\"IsUnit\":0,\"ReplaceImg\":\"1\",\"LightProperty\":\"\",\"MaterialType\":\"Furniture\",\"MaterialCategoryName\":\"\",\"DesignCode\":\"CP020099\",\"MaterialId\":\"154152227\",\"PartsName\":\"\",\"Parts\":\"\",\"Is3D\":1,\"MaterialName\":\"诺岩电视柜\",\"ProductId\":\"51477435\",\"PICSize\":\"1784.96X375.54X395.39\",\"PICLength\":1784.96,\"PICWidth\":375.54,\"PICHeight\":395.39,\"ModelDepth\":0,\"CategoryId\":106699719,\"OrganId\":\"C00002400\",\"DeptId\":\"00388632\",\"Author\":\"1000317743\",\"PostTime\":\"2022-04-07 11:31:47\",\"IsDelete\":0,\"ImagePath\":\"/UpFile/C00002400/DesignMaterial/202204/07/154152227/VRenderImage_1738933210.png\",\"ExpandAttrib\":\"\",\"IsShare\":0,\"IsPublic\":0,\"ExtendField\":\"\",\"ModelFlag\":1,\"CostPrice\":0.0,\"WholesalePrice\":0.0,\"RetailPrice\":0.0,\"IsRender\":0,\"PlaceRule\":\"stickFloor\",\"Property\":\"请选择\",\"HoleLength\":0,\"HoleWidth\":0,\"PlaceHeight\":\"\",\"Content\":\"\",\"IsA3d\":\"1\",\"XmlContent\":\"\",\"MaterialCategory\":\"\",\"ContentCloudUrl\":null,\"ContentCDNUrl\":null,\"XmlContentCloudUrl\":null,\"XmlContentCDNUrl\":null,\"SpecialTexture\":\"\",\"MixPaveSetting\":\"\",\"MaterialAttribute\":\"\",\"BelongType\":\"2\",\"Version\":0,\"BrickId\":\"\",\"tenant_id\":\"C00000022\"}]},\"success\":true}";
        String json3="{\"result\":{\"recordCount\":1,\"result\":[{\"AuthCode\":\"\",\"CADUrl\":\"\",\"CategoryName\":\"电视柜\",\"DoorTypeSetting\":0,\"LeafType\":\"\",\"MaterialList\":[],\"Model\":null,\"OpenDirection\":\"\",\"Orientation\":\"0\",\"ParentMaterialIdList\":null,\"RelationList\":[],\"TagIdList\":[],\"ThumbPath\":null,\"UploadFiles\":null,\"channelIsOut\":0,\"designMaterialTagIds\":\"19650,18418,873580,873422\",\"isCollect\":0,\"isGlb\":0,\"isLock\":0,\"isOut\":0,\"isQuality\":0,\"quoteCode\":\"ClobField/DesignMaterial/quoteCode/20220418/FYD7EGZKzb1650250548535.txt\",\"remark\":\"测试备注\",\"salePrice\":0.0,\"versionTime\":\"2022-04-18 10:55:50\",\"WCCId\":\"\",\"ModifyUserId\":\"1100241524\",\"ModifyTime\":\"2022-04-1810:55:50\",\"Color\":\"\",\"IShasProduct\":0,\"FreeCategoryId\":106699719,\"SubParts\":\"\",\"WidthMax\":0.0,\"WidthMin\":0.0,\"WidthDefault\":\"\",\"DepthMax\":0.0,\"DepthMin\":0.0,\"DepthDefault\":\"\",\"HeightMax\":0.0,\"HeightMin\":0.0,\"HeightDefault\":\"\",\"SortNumber\":0,\"RawMaterialType\":\"\",\"UseArea\":\"\",\"UseCategory\":\"\",\"UseStage\":\"\",\"A3dSource\":\"/UpFile/C00002400/PMC/DesignMaterial/202204/154152227/VR154152227.svj?version=1649304078849\",\"IsTop\":0,\"IsRelate\":0,\"IsUnit\":0,\"ReplaceImg\":\"1\",\"LightProperty\":\"\",\"MaterialType\":\"Furniture\",\"MaterialCategoryName\":\"\",\"DesignCode\":\"CP020099\",\"MaterialId\":\"154152227\",\"PartsName\":\"\",\"Parts\":\"\",\"Is3D\":1,\"MaterialName\":\"诺岩电视柜\",\"ProductId\":\"51477435\",\"PICSize\":\"1784.96X375.54X395.39\",\"PICLength\":1784.96,\"PICWidth\":375.54,\"PICHeight\":395.39,\"ModelDepth\":0,\"CategoryId\":106699719,\"OrganId\":\"C00002400\",\"DeptId\":\"00388632\",\"Author\":\"1000317743\",\"PostTime\":\"2022-04-07 11:31:47\",\"IsDelete\":0,\"ImagePath\":\"/UpFile/C00002400/DesignMaterial/202204/07/154152227/VRenderImage_1738933210.png\",\"ExpandAttrib\":\"\",\"IsShare\":0,\"IsPublic\":0,\"ExtendField\":\"\",\"ModelFlag\":1,\"CostPrice\":0.0,\"WholesalePrice\":0.0,\"RetailPrice\":0.0,\"IsRender\":0,\"PlaceRule\":\"stickFloor\",\"Property\":\"请选择\",\"HoleLength\":0,\"HoleWidth\":0,\"PlaceHeight\":\"\",\"Content\":\"\",\"IsA3d\":\"1\",\"XmlContent\":\"\",\"MaterialCategory\":\"\",\"ContentCloudUrl\":null,\"ContentCDNUrl\":null,\"XmlContentCloudUrl\":null,\"XmlContentCDNUrl\":null,\"SpecialTexture\":\"\",\"MixPaveSetting\":\"\",\"MaterialAttribute\":\"\",\"BelongType\":\"2\",\"Version\":0,\"BrickId\":\"\"}]},\"success\":true}";
        String json4="{\"result\":{\"recordCount\":1,\"result\":[{\"AuthCode\":\"\",\"CADUrl\":\"\",\"DoorTypeSetting\":0,\"CategoryName\":\"电视柜\",\"LeafType\":\"\",\"MaterialList\":[],\"Model\":null,\"OpenDirection\":\"\",\"Orientation\":\"0\",\"ParentMaterialIdList\":null,\"RelationList\":[],\"TagIdList\":[],\"ThumbPath\":null,\"UploadFiles\":null,\"channelIsOut\":0,\"designMaterialTagIds\":\"19650,18418,873580,873422\",\"isCollect\":0,\"isGlb\":0,\"isLock\":0,\"isOut\":0,\"isQuality\":0,\"quoteCode\":\"ClobField/DesignMaterial/quoteCode/20220418/FYD7EGZKzb1650250548535.txt\",\"remark\":\"测试备注\",\"salePrice\":0.0,\"versionTime\":\"2022-04-18 10:55:50\",\"WCCId\":\"\",\"ModifyUserId\":\"1100241524\",\"ModifyTime\":\"2022-04-1810:55:50\",\"Color\":\"\",\"IShasProduct\":0,\"FreeCategoryId\":106699719,\"SubParts\":\"\",\"WidthMax\":0.0,\"WidthMin\":0.0,\"WidthDefault\":\"\",\"DepthMax\":0.0,\"DepthMin\":0.0,\"DepthDefault\":\"\",\"HeightMax\":0.0,\"HeightMin\":0.0,\"HeightDefault\":\"\",\"SortNumber\":0,\"RawMaterialType\":\"\",\"UseArea\":\"\",\"UseCategory\":\"\",\"UseStage\":\"\",\"A3dSource\":\"/UpFile/C00002400/PMC/DesignMaterial/202204/154152227/VR154152227.svj?version=1649304078849\",\"IsTop\":0,\"IsRelate\":0,\"IsUnit\":0,\"ReplaceImg\":\"1\",\"LightProperty\":\"\",\"MaterialType\":\"Furniture\",\"MaterialCategoryName\":\"\",\"DesignCode\":\"CP020099\",\"MaterialId\":\"154152227\",\"PartsName\":\"\",\"Parts\":\"\",\"Is3D\":1,\"MaterialName\":\"诺岩电视柜\",\"ProductId\":\"51477435\",\"PICSize\":\"1784.96X375.54X395.39\",\"PICLength\":1784.96,\"PICWidth\":375.54,\"PICHeight\":395.39,\"ModelDepth\":0,\"CategoryId\":106699719,\"OrganId\":\"C00002400\",\"DeptId\":\"00388632\",\"Author\":\"1000317743\",\"PostTime\":\"2022-04-07 11:31:47\",\"IsDelete\":0,\"ImagePath\":\"/UpFile/C00002400/DesignMaterial/202204/07/154152227/VRenderImage_1738933210.png\",\"ExpandAttrib\":\"\",\"IsShare\":0,\"IsPublic\":0,\"ExtendField\":\"\",\"ModelFlag\":1,\"CostPrice\":0.0,\"WholesalePrice\":0.0,\"RetailPrice\":0.0,\"IsRender\":0,\"PlaceRule\":\"stickFloor\",\"Property\":\"请选择\",\"HoleLength\":0,\"HoleWidth\":0,\"PlaceHeight\":\"\",\"Content\":\"\",\"IsA3d\":\"1\",\"XmlContent\":\"\",\"MaterialCategory\":\"\",\"ContentCloudUrl\":null,\"ContentCDNUrl\":null,\"XmlContentCloudUrl\":null,\"XmlContentCDNUrl\":null,\"SpecialTexture\":\"\",\"MixPaveSetting\":\"\",\"MaterialAttribute\":\"\",\"BelongType\":\"2\",\"Version\":0,\"BrickId\":\"\"}]},\"success\":true}";

        String json5="{\"result\":{\"recordCount\":1,\"result\":[{\"AuthCode\":\"1\",\"CADUrl\":\"2\"},{\"AuthCode\":\"3\",\"CADUrl\":\"4\"}]},\"success\":true}";
        String json6="{\"result\":{\"recordCount\":1,\"result\":[{\"AuthCode\":\"3\",\"CADUrl\":\"4\"},{\"AuthCode\":\"1\",\"CADUrl\":\"2\"}]},\"success\":true}";
        String json7="{\"result\":{\"recordCount\":1,\"result\":[{\"AuthCode\":\"3\",\"CADUrl\":\"4\",\"tenant_id\":\"C00000098\"},{\"AuthCode\":\"1\",\"CADUrl\":\"2\"}]},\"success\":true}";

        String json8="[{\"AuthCode\":\"3\",\"CADUrl\":\"4\"},{\"AuthCode\":\"1\",\"CADUrl\":\"2\"}]";
        String json9="[{\"AuthCode\":\"3\",\"CADUrl\":\"4\",\"tenant_id\":\"C00000098\"},{\"AuthCode\":\"1\",\"CADUrl\":\"2\",\"shop_id\":\"C00000098\"}]";

        String compareResult1=compare(json1,json1,true); //字符串比对相等
        System.out.println(compareResult1);
        System.out.println("compare 1 end-----------------------------------------");

        String compareResult2=compare(json1,json2); //后者去掉tenantId,shopId,createId再进行Json对比,结果：不相等
        System.out.println(compareResult2);
        System.out.println("compare 2 end-----------------------------------------");

        String compareResult3=compare(json3,json2); //后者去掉tenantId,shopId,createId再进行Json对比,结果：相等
        System.out.println(compareResult3);
        System.out.println("compare 3 end-----------------------------------------");

        String compareResult4=compare(json3,json4); //object内key有移动，但是Json对比相等
        System.out.println(compareResult4);
        System.out.println("compare 4 end-----------------------------------------");

        String compareResult5=compare(json5,json6); //json内数组有移动
        System.out.println(compareResult5);
        System.out.println("compare 5 end-----------------------------------------");

        String compareResult6=compare(json5,json7); //后者去掉tenantId,shopId,createId再进行Json对比,结果:json内数组有移动
        System.out.println(compareResult6);
        System.out.println("compare 6 end-----------------------------------------");

        String compareResult8=compare(json8,json9); //后者去掉tenantId,shopId,createId再进行Json对比,结果:相等
        System.out.println(compareResult8);
        System.out.println("compare 8 end-----------------------------------------");
    }

    public final static String[] keys={"tenantid","tenantId","tenantID","tenant_id","tenant_Id","tenant_ID",
            "Tenantid","TenantId","TenantID","Tenant_id","Tenant_Id","Tenant_ID",
            "shopid","shopId","shopID","shop_id","shop_Id","shop_ID",
            "Shopid","ShopId","ShopID","Shop_id","Shop_Id","Shop_ID",
            "creatorid","creatorId","creatorID","creator_id","creator_Id","creator_ID",
            "Creatorid","CreatorId","CreatorID","Creator_id","Creator_Id","Creator_ID"
    };
    private final static ObjectMapper objectMapper=new ObjectMapper();

    private static JsonNode removeKeys(JsonNode node){
        ObjectNode obj1= (ObjectNode) node;
        for(String key:keys)
            obj1.remove(key);
        return node;
    }

    private static void traverseRemove(JsonNode node){
        if(node.isObject()) {
            removeKeys(node);

            Iterator<Map.Entry<String, JsonNode>> fields = node.fields();
            while (fields.hasNext()) {
                Map.Entry<String, JsonNode> jsonField = fields.next();
                JsonNode nextNode = jsonField.getValue();
                if(nextNode.isObject())
                    traverseRemove(nextNode);
                else if(nextNode.isArray()){
                    Iterator<JsonNode> jsonNodeIterator=nextNode.elements();
                    while (jsonNodeIterator.hasNext()){
                        traverseRemove(jsonNodeIterator.next());
                    }
                }else{
                    continue;
                }
            }
        }else if(node.isArray()){
            Iterator<JsonNode> jsonNodeIterator=node.elements();
            while (jsonNodeIterator.hasNext()){
                traverseRemove(jsonNodeIterator.next());
            }
        }else {
        }
    }

    private static JsonNode zjsonpatchDiff(String jsonStr1,String jsonStr2){
        try {
            JsonNode node1=objectMapper.readTree(jsonStr1);
            JsonNode node2=objectMapper.readTree(jsonStr2);
            SortJson.traverseSortArrayNode(node1);
            SortJson.traverseSortArrayNode(node2);
            return zjsonpatchDiffNode(node1,node2);
        }catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static JsonNode zjsonpatchDiffNode(JsonNode node1,JsonNode node2){
        EnumSet<DiffFlags> flags = DiffFlags.defaults();
        try {
            JsonNode patchNode = JsonDiff.asJson(node1, node2, flags);
//            System.out.println(patchNode.toString());
            return patchNode;
        }catch (Exception e){
            e.printStackTrace();
            System.err.println("node1: "+node1.toString());
            System.err.println("node2: "+node2.toString());
            ObjectNode objNode = objectMapper.createObjectNode();
            objNode.put("op", "custom_exception");
            objNode.put("detail", "unhandled token type NOT_AVAILABLE");
            ArrayNode node = objectMapper.createArrayNode();
            node.add(objNode);
            return node;
        }
    }

    private static String lastEstimateJsonDiff(JsonNode diff){
        if(diff.size()==0){
            return ComparedResult.JSON_LAST_EQUALS.toString();
        }else {
            if(diff.isArray()){
                Iterator<JsonNode> iterator=diff.elements();
                while (iterator.hasNext()){
                    JsonNode opNode=iterator.next().get("op");
                    if(opNode==null){
                        return ComparedResult.DIFF_NOT_CERTAIN.toString();
                    }
                    if(opNode.asText()=="custom_exception"){
                        return ComparedResult.DIFF_EXCEPTION.toString();
                    }
                    if(opNode.asText()!="move" && opNode.asText()!="custom_exception" ){
                        return  ComparedResult.JSON_LAST_NOT_EQUALS.toString();
                    }
                }
                return ComparedResult.JSON_MOVE.toString();
            }else {
                return ComparedResult.DIFF_NOT_CERTAIN.toString();
            }
        }
    }

    private static String estimateJsonDiff(JsonNode diffNode,String oldRepsonse,String newRepsonse){
        if(diffNode.size()==0){
            return ComparedResult.JSON_EQUALS.toString();
        }else {
            if(diffNode.isArray()){
                Iterator<JsonNode> jsonNodeIterator=diffNode.elements();
                while (jsonNodeIterator.hasNext()){
                    JsonNode opNode=jsonNodeIterator.next().get("op");
                    if(opNode==null){
                        return ComparedResult.DIFF_NOT_CERTAIN.toString();
                    }
                    if(opNode.asText()=="custom_exception"){
                        return ComparedResult.DIFF_EXCEPTION.toString();
                    }
                    if(opNode.asText()!="move" && opNode.asText()!="custom_exception"){
//                        System.out.println("转成Json后对比不相等,尝试把后者的指定key移除后再比较一次");
                        try {
                            JsonNode node1=objectMapper.readTree(oldRepsonse);
                            JsonNode node2=objectMapper.readTree(newRepsonse);
                            traverseRemove(node2);
                            SortJson.traverseSortArrayNode(node1);
                            SortJson.traverseSortArrayNode(node2);
                            JsonNode diff=zjsonpatchDiffNode(node1,node2);
                            String result=lastEstimateJsonDiff(diff);
                            if(result.equals(ComparedResult.JSON_LAST_NOT_EQUALS.toString())){
                                ArrayList<Integer> sizeList1=new ArrayList<>();
                                traverseArraySize(node1,sizeList1);
                                int sum1=sizeList1.stream().mapToInt(i->i.intValue()).sum();

                                ArrayList<Integer> sizeList2=new ArrayList<>();
                                traverseArraySize(node2,sizeList2);
                                int sum2=sizeList2.stream().mapToInt(i->i.intValue()).sum();

                                result=String.format("%s,数组size分别是 %d , %d",result,sum1,sum2);
                            }
                            return result;
                        } catch (JsonProcessingException e) {
                            e.printStackTrace();
                        }
                    }
                }
                return ComparedResult.JSON_MOVE.toString();
            }else {
                return ComparedResult.DIFF_NOT_CERTAIN.toString();
            }
        }
    }

    private static String compare(String oldRepsonse,String newRepsonse){
        if (oldRepsonse == null || newRepsonse == null) {
            return ComparedResult.NULL_OBJECT.toString();
        }

        if(oldRepsonse.equals(newRepsonse))
            return ComparedResult.STR_EQUALS.toString();
        else{
            if(ReadExcelUtilsApi.isJSON(oldRepsonse) && ReadExcelUtilsApi.isJSON(newRepsonse)){
                JsonNode diffNode=zjsonpatchDiff(oldRepsonse,newRepsonse);
                return estimateJsonDiff(diffNode,oldRepsonse,newRepsonse);
            }else {
                return ComparedResult.NOT_JSON.toString();
            }
        }
    }

    public static String compare(String oldRepsonse,String newRepsonse,boolean isWeb3D){
        if(oldRepsonse.equals(newRepsonse))
            return ComparedResult.STR_EQUALS.toString();

        boolean isJson1=ReadExcelUtilsApi.isJSON(oldRepsonse);
        boolean isJson2=ReadExcelUtilsApi.isJSON(newRepsonse);

        if(isJson1 && isJson2){
            if(isWeb3D){
                try {
                    JsonNode node1=objectMapper.readTree(oldRepsonse);
                    JsonNode node2=objectMapper.readTree(newRepsonse);
                    String oldRepsonseJSON=getWeb3DResultJson(node1);
                    String newRepsonseJSON=getWeb3DResultJson(node2);
                    if(oldRepsonseJSON ==null){
                        oldRepsonseJSON=oldRepsonse;
                    }
                    if(newRepsonseJSON ==null){
                        newRepsonseJSON=newRepsonse;
                    }
                    return compare(oldRepsonseJSON,newRepsonseJSON);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                    return oldRepsonse.equals(newRepsonse)?ComparedResult.STR_EQUALS.toString():ComparedResult.STR_NOT_EQUALS.toString();
                }
            }else{
                return compare(oldRepsonse,newRepsonse);
            }
        }else{
            return oldRepsonse.equals(newRepsonse)?ComparedResult.STR_EQUALS.toString():ComparedResult.STR_NOT_EQUALS.toString();
        }
    }

    private static String getWeb3DResultJson(JsonNode node){
        String responseJson=null;
        if(node.isObject()){
            if(node.get("JSON")!=null){
                responseJson=node.get("JSON").asText();
            }else if(node.get("Json")!=null){
                responseJson=node.get("Json").asText();
            }
            else if(node.get("json")!=null){
                responseJson=node.get("json").asText();
            }
        }
        return responseJson;
    }

    private static void traverseArraySize(JsonNode node, ArrayList<Integer> sizeList){
        if(node.isObject()) {
            Iterator<Map.Entry<String, JsonNode>> fields = node.fields();
            while (fields.hasNext()) {
                Map.Entry<String, JsonNode> jsonField = fields.next();
                JsonNode nextNode = jsonField.getValue();
                if(nextNode.isObject())
                    traverseArraySize(nextNode,sizeList);
                else if(nextNode.isArray()){
                    int size=nextNode.size();
                    sizeList.add(size);
                    Iterator<JsonNode> jsonNodeIterator=nextNode.elements();
                    while (jsonNodeIterator.hasNext()){
                        traverseArraySize(jsonNodeIterator.next(),sizeList);
                    }
                }else{
                    continue;
                }
            }
        }else if(node.isArray()){
            int size=node.size();
            sizeList.add(size);
            Iterator<JsonNode> jsonNodeIterator=node.elements();
            while (jsonNodeIterator.hasNext()){
                traverseArraySize(jsonNodeIterator.next(),sizeList);
            }
        }
    }

    public static HashMap<String,Long>  getFailedCountMap(ArrayList<Map> resultList){
        Predicate<Map> predicate=new Predicate<Map>() {
            @Override
            public boolean test(Map map) {
                if(map.get("compare").toString().startsWith(ComparedResult.STR_EQUALS.toString()))return false;
                if(map.get("compare").toString().startsWith(ComparedResult.JSON_EQUALS.toString()))return false;
                if(map.get("compare").toString().startsWith(ComparedResult.JSON_MOVE.toString()))return false;
                if(map.get("compare").toString().startsWith(ComparedResult.JSON_LAST_EQUALS.toString()))return false;
                return true;
            }
        };
        HashMap<String,Long> map=new HashMap<String,Long>();
        if(resultList.size()<1)return map;

        long count=resultList.stream().filter(predicate).count();
        map.put(resultList.get(0).get("stringUrlNew").toString(),Long.valueOf(count));
        return map;
    }
}
