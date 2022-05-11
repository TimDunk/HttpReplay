package com.c3stones.utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.*;

public class SortJson {
    final static ObjectMapper objectMapper=new ObjectMapper();

    public static void main(String[] args){
        String json1="{\"result\":[{\"modifyTime\":1550053499000,\"CategoryDescribe\":\"\",\"ObjectId\":\"\",\"ObjectType\":\"\",\"ExpandData\":\"\",\"CategoryId\":7320125,\"CategoryName\":\"立体装置画\",\"ParentId\":16283,\"LeftValue\":0,\"RightValue\":0,\"IsDelete\":0,\"OrganId\":\"C00000022\",\"Module\":\"DesignMaterial\",\"CategoryCode\":\"\",\"ImagePath\":\"\",\"SortCode\":13,\"MaterialAttribute\":\"\",\"subList\":[{\"modifyTime\":1550053499000,\"CategoryDescribe\":\"\",\"ObjectId\":\"\",\"ObjectType\":\"\",\"ExpandData\":\"\",\"CategoryId\":7320125,\"CategoryName\":\"立体装置画\",\"ParentId\":16283,\"LeftValue\":0,\"RightValue\":0,\"IsDelete\":0,\"OrganId\":\"C00000022\",\"Module\":\"DesignMaterial\",\"CategoryCode\":\"\",\"ImagePath\":\"\",\"SortCode\":13,\"MaterialAttribute\":\"\"},{\"modifyTime\":1544522860000,\"CategoryDescribe\":\"\",\"ObjectId\":\"0\",\"ObjectType\":\"\",\"ExpandData\":\"\",\"CategoryId\":133500,\"CategoryName\":\"装饰镜\",\"ParentId\":7075178,\"LeftValue\":0,\"RightValue\":0,\"IsDelete\":0,\"OrganId\":\"C00000022\",\"Module\":\"DesignMaterial\",\"CategoryCode\":\"\",\"ImagePath\":\"\",\"SortCode\":13,\"MaterialAttribute\":\"\"},{\"modifyTime\":1550114757000,\"CategoryDescribe\":\"\",\"ObjectId\":\"\",\"ObjectType\":\"\",\"ExpandData\":\"\",\"CategoryId\":7320791,\"CategoryName\":\"其它\",\"ParentId\":16283,\"LeftValue\":0,\"RightValue\":0,\"IsDelete\":0,\"OrganId\":\"C00000022\",\"Module\":\"DesignMaterial\",\"CategoryCode\":\"\",\"ImagePath\":\"\",\"SortCode\":14,\"MaterialAttribute\":\"\"}]},{\"modifyTime\":1544522860000,\"CategoryDescribe\":\"\",\"ObjectId\":\"0\",\"ObjectType\":\"\",\"ExpandData\":\"\",\"CategoryId\":133500,\"CategoryName\":\"装饰镜\",\"ParentId\":7075178,\"LeftValue\":0,\"RightValue\":0,\"IsDelete\":0,\"OrganId\":\"C00000022\",\"Module\":\"DesignMaterial\",\"CategoryCode\":\"\",\"ImagePath\":\"\",\"SortCode\":13,\"MaterialAttribute\":\"\",\"subList\":[{\"modifyTime\":1550053499000,\"CategoryDescribe\":\"\",\"ObjectId\":\"\",\"ObjectType\":\"\",\"ExpandData\":\"\",\"CategoryId\":7320125,\"CategoryName\":\"立体装置画\",\"ParentId\":16283,\"LeftValue\":0,\"RightValue\":0,\"IsDelete\":0,\"OrganId\":\"C00000022\",\"Module\":\"DesignMaterial\",\"CategoryCode\":\"\",\"ImagePath\":\"\",\"SortCode\":13,\"MaterialAttribute\":\"\"},{\"modifyTime\":1544522860000,\"CategoryDescribe\":\"\",\"ObjectId\":\"0\",\"ObjectType\":\"\",\"ExpandData\":\"\",\"CategoryId\":133500,\"CategoryName\":\"装饰镜\",\"ParentId\":7075178,\"LeftValue\":0,\"RightValue\":0,\"IsDelete\":0,\"OrganId\":\"C00000022\",\"Module\":\"DesignMaterial\",\"CategoryCode\":\"\",\"ImagePath\":\"\",\"SortCode\":13,\"MaterialAttribute\":\"\"},{\"modifyTime\":1550114757000,\"CategoryDescribe\":\"\",\"ObjectId\":\"\",\"ObjectType\":\"\",\"ExpandData\":\"\",\"CategoryId\":7320791,\"CategoryName\":\"其它\",\"ParentId\":16283,\"LeftValue\":0,\"RightValue\":0,\"IsDelete\":0,\"OrganId\":\"C00000022\",\"Module\":\"DesignMaterial\",\"CategoryCode\":\"\",\"ImagePath\":\"\",\"SortCode\":14,\"MaterialAttribute\":\"\"}]},{\"modifyTime\":1550114757000,\"CategoryDescribe\":\"\",\"ObjectId\":\"\",\"ObjectType\":\"\",\"ExpandData\":\"\",\"CategoryId\":7320791,\"CategoryName\":\"其它\",\"ParentId\":16283,\"LeftValue\":0,\"RightValue\":0,\"IsDelete\":0,\"OrganId\":\"C00000022\",\"Module\":\"DesignMaterial\",\"CategoryCode\":\"\",\"ImagePath\":\"\",\"SortCode\":14,\"MaterialAttribute\":\"\",\"subList\":[{\"modifyTime\":1550053499000,\"CategoryDescribe\":\"\",\"ObjectId\":\"\",\"ObjectType\":\"\",\"ExpandData\":\"\",\"CategoryId\":7320125,\"CategoryName\":\"立体装置画\",\"ParentId\":16283,\"LeftValue\":0,\"RightValue\":0,\"IsDelete\":0,\"OrganId\":\"C00000022\",\"Module\":\"DesignMaterial\",\"CategoryCode\":\"\",\"ImagePath\":\"\",\"SortCode\":13,\"MaterialAttribute\":\"\"},{\"modifyTime\":1544522860000,\"CategoryDescribe\":\"\",\"ObjectId\":\"0\",\"ObjectType\":\"\",\"ExpandData\":\"\",\"CategoryId\":133500,\"CategoryName\":\"装饰镜\",\"ParentId\":7075178,\"LeftValue\":0,\"RightValue\":0,\"IsDelete\":0,\"OrganId\":\"C00000022\",\"Module\":\"DesignMaterial\",\"CategoryCode\":\"\",\"ImagePath\":\"\",\"SortCode\":13,\"MaterialAttribute\":\"\"},{\"modifyTime\":1550114757000,\"CategoryDescribe\":\"\",\"ObjectId\":\"\",\"ObjectType\":\"\",\"ExpandData\":\"\",\"CategoryId\":7320791,\"CategoryName\":\"其它\",\"ParentId\":16283,\"LeftValue\":0,\"RightValue\":0,\"IsDelete\":0,\"OrganId\":\"C00000022\",\"Module\":\"DesignMaterial\",\"CategoryCode\":\"\",\"ImagePath\":\"\",\"SortCode\":14,\"MaterialAttribute\":\"\"}]}],\"success\":true}";
        try {
            JsonNode node=objectMapper.readTree(json1);
//            System.out.println("排序前");
//            System.out.println(node.toPrettyString());
            traverseSortArrayNode(node);
            System.out.println("排序后");
            System.out.println(node.toPrettyString());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    private static boolean matchSortedKey(String key){
        ArrayList<String> list=new ArrayList<>();
        list.add("id");
        list.add("schemeid");
        list.add("materialid");
        list.add("categoryid");
        list.add("tagid");
        list.add("roleid");
        list.add("recordid");
        list.add("relationid");
        list.add("parentId");
        list.add("sortCode");
        list.add("sort");
        String lowerCaseKey=key.toLowerCase();
        return list.contains(lowerCaseKey);
    }

    private static Comparator getComparator(){
        return new Comparator<Object>() {
            @Override
            public int compare(Object o1, Object o2) {
                if(o1 instanceof LinkedHashMap && o2 instanceof LinkedHashMap){
                    LinkedHashMap map1=(LinkedHashMap) o1;
                    LinkedHashMap map2=(LinkedHashMap) o2;

                    int size1=map1.size();
                    int size2=map2.size();
                    if(size1<size2)return -1;
                    if(size1>size2)return 1;

                    Iterator iter=map1.entrySet().iterator();
                    while(iter.hasNext()){
                        Map.Entry entry=(Map.Entry)iter.next();
                        Object key=entry.getKey();
                        if(matchSortedKey((String)key) && map2.containsKey(key) ){
                            Object val1=entry.getValue();
                            Object val2=map2.get(key);
                            if(val1 instanceof Integer && val2 instanceof Integer){
                                return ((Integer) val1).compareTo(((Integer) val2));
                            }
                            if(val1 instanceof String && val2 instanceof String){
                                String strVal1=(String)val1;
                                String strVal2=(String)val2;
                                return strVal1.compareTo(strVal2);
                            }
                        }
                    }
                }
                if(o1 instanceof ArrayList && o2 instanceof ArrayList){
                    int size1=((ArrayList)o1).size();
                    int size2=((ArrayList)o2).size();
                    if(size1<size2)return -1;
                    if(size1>size2)return 1;
                }
                if(o1 instanceof String && o2 instanceof String){
                    String s1=(String)o1;
                    String s2=(String)o2;
                    return s1.compareTo(s2);
                }
                if(o1 instanceof Integer && o2 instanceof Integer){
                    return ((Integer) o1).compareTo(((Integer) o2));
                }

                return 0;
            }
        };
    }

    private static void getSortedArrayNode(ArrayNode arrayNode){
        List<Object> objectList=null;
        try {
            objectList=objectMapper.readValue(arrayNode.traverse(),new TypeReference<List<Object>>(){});
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
        Comparator cmp=getComparator();
        objectList.sort(cmp);  //List排序
        try {
            //List排序后转成ArrayNode
            StringWriter stringWriter=new StringWriter();
            JsonGenerator jsonGenerator = objectMapper.createGenerator(stringWriter);
            objectMapper.writeValue(jsonGenerator,objectList);
            StringReader stringReader=new StringReader(stringWriter.getBuffer().toString());
            JsonNode node=objectMapper.readTree(stringReader);
            if(node.isArray()&& !node.isEmpty()){
                arrayNode.removeAll();
                arrayNode.addAll((ArrayNode)node);
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException io){
            io.printStackTrace();
        }
    }

    public static void traverseSortArrayNode(JsonNode node){
        if(node.isObject()) {
            Iterator<Map.Entry<String, JsonNode>> fields = node.fields();
            while (fields.hasNext()) {
                Map.Entry<String, JsonNode> jsonField = fields.next();
                JsonNode nextNode = jsonField.getValue();
                if(nextNode.isObject())
                    traverseSortArrayNode(nextNode);
                else if(nextNode.isArray()){
                    getSortedArrayNode((ArrayNode) nextNode);
                    traverseSortArrayNode(nextNode);
                }else{
                    continue;
                }
            }
        }else if(node.isArray()){
            if(!node.isEmpty()){
                getSortedArrayNode((ArrayNode) node);
                Iterator<JsonNode> jsonNodeIterator=node.elements();
                while (jsonNodeIterator.hasNext()){
                    traverseSortArrayNode(jsonNodeIterator.next());
                }
            }
        }else {
        }
    }
}
