package com.c3stones;

import com.c3stones.entity.FileExport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Constants {

    public  static List<FileExport> fileList = new ArrayList();
    public  static List<FileExport> fileApiList = new ArrayList();

    static {
//        //1020 web接口
//        fileList.add(new FileExport("favorite_getFavorieList.xls","favorite_getFavorieList_result2003.xls"));
//        fileList.add(new FileExport("IsHaveCRMAdministratorRole.xls","IsHaveCRMAdministratorRole_result2003.xls"));
//        fileList.add(new FileExport("security_getOrganLogo.xls","security_getOrganLogo_result2003.xls"));
//        fileList.add(new FileExport("security_getUserInfo.xls","security_getUserInfo_result2003.xls"));
//        fileList.add(new FileExport("systemsetting_getQuotationsOperation.xls","systemsetting_getQuotationsOperation_result2003.xls"));
//
//        //1020 api接口
//        fileApiList.add(new FileExport("uc_get.xls","uc_get_result2003.xls"));
//        fileApiList.add(new FileExport("uc_getPayStatusInfo.xls","uc_getPayStatusInfo_result2003.xls"));
//        fileApiList.add(new FileExport("uc_getRoleListByUserId.xls","uc_getRoleListByUserId_result2003.xls"));
//        fileApiList.add(new FileExport("uc_getShopByDeptId.xls","uc_getShopByDeptId_result2003.xls"));

        //1027 web接口
//          fileList.add(new FileExport("designmaterial_getCategoryAndCloudMaterialListWithOutContent.xls","designmaterial_getCategoryAndCloudMaterialListWithOutContent_result2003.xls"));
//          fileList.add(new FileExport("designmaterial_getCategoryAndMaterialList.xls","designmaterial_getCategoryAndMaterialList_result2003.xls"));
//          fileList.add(new FileExport("designmaterial_getCategoryAndMaterialListNew.xls","designmaterial_getCategoryAndMaterialListNew.xls_result2003.xls"));
//          fileList.add(new FileExport("designmaterial_getCategoryAndMaterialListWithOutContent.xls","designmaterial_getCategoryAndMaterialListWithOutContent_result2003.xls"));
//          fileList.add(new FileExport("designmaterial_getDesignMaterialListByCategoryIdEx.xls","designmaterial_getDesignMaterialListByCategoryIdEx_result2003.xls"));
//          fileList.add(new FileExport("designmaterial_getDesignMaterialListByCategoryIdExByEs.xls","designmaterial_getDesignMaterialListByCategoryIdExByEs_result2003.xls"));
//          fileList.add(new FileExport("designmaterial_getDesignMaterialListByCategoryIdForRecommend.xls","designmaterial_getDesignMaterialListByCategoryIdForRecommend_result2003.xls"));
//          fileList.add(new FileExport("designmaterial_getDesignMaterialListByCategoryIdForRecommendNew.xls","designmaterial_getDesignMaterialListByCategoryIdForRecommendNew_result2003.xls"));
//          fileList.add(new FileExport("designmaterial_getDesignMaterialListByCategoryIdForReplace.xls","designmaterial_getDesignMaterialListByCategoryIdForReplace_result2003.xls"));
//          fileList.add(new FileExport("designmaterial_getDesignMaterialListByCategoryIdForSearch.xls","designmaterial_getDesignMaterialListByCategoryIdForSearch_result2003.xls"));
//          fileList.add(new FileExport("designmaterial_getDesignMaterialListByCategoryIdForSearchNew.xls","designmaterial_getDesignMaterialListByCategoryIdForSearchNew_result2003.xls"));
//          fileList.add(new FileExport("designmaterial_getDesignMaterialListByCategoryIdWithAuthority.xls","designmaterial_getDesignMaterialListByCategoryIdWithAuthority_result2003.xls"));
//          fileList.add(new FileExport("designmaterial_getDesignMaterialListForMyMine.xls","designmaterial_getDesignMaterialListForMyMine_result2003.xls"));
//          fileList.add(new FileExport("designmaterial_getListForAssistantByCondition.xls","designmaterial_getListForAssistantByCondition_result2003.xls"));

        //1027 api 接口
//        fileApiList.add(new FileExport("designmaterial_getDesignMaterialListForMyMine.xls","designmaterial_getDesignMaterialListForMyMine_result2003.xls"));
//        fileApiList.add(new FileExport("designmaterial_getListForAssistantByCondition.xls","designmaterial_getListForAssistantByCondition_result2003.xls"));

        //第二批接口
        //1223 web接口
//        fileList.add(new FileExport("W3D#Building_GetPublicRoomModelList.xls","W3D#Building_GetPublicRoomModelList_result2003.xls"));
//        fileList.add(new FileExport("W3D#Category_getEnterpriseCategory.xls","W3D#Category_getEnterpriseCategory_result2003.xls"));
//        fileList.add(new FileExport("W3D#Category_GetPrivateCategoryList.xls","W3D#Category_GetPrivateCategoryList_result2003.xls"));
//        fileList.add(new FileExport("W3D#Category_GetPrivateSubCategoryListByCodeForBrand.xls","W3D#Category_GetPrivateSubCategoryListByCodeForBrand_result2003.xls"));
//        fileList.add(new FileExport("W3D#Category_GetPrivateSubCategoryListByCodeForWardrobe.xls","W3D#Category_GetPrivateSubCategoryListByCodeForWardrobe_result2003.xls"));
//        fileList.add(new FileExport("W3D#Category_GetStoreCategory.xls","W3D#Category_GetStoreCategory_result2003.xls"));
//        fileList.add(new FileExport("W3D#Category_GetSubCategoryList.xls","W3D#Category_GetSubCategoryList_result2003.xls"));
//        fileList.add(new FileExport("W3D#Category_GetSubCategoryListByCode.xls","W3D#Category_GetSubCategoryListByCode_result2003.xls"));
//        fileList.add(new FileExport("W3D#DesignMaterial_GetDesignMaterialListByCategoryIdForBrand_v2.xls","W3D#DesignMaterial_GetDesignMaterialListByCategoryIdForBrand_v2_result2003.xls"));
//
//        fileList.add(new FileExport("W3D#DesignMaterial_GetDesignMaterialListByCategoryIdForStore.xls","W3D#DesignMaterial_GetDesignMaterialListByCategoryIdForStore_result2003.xls"));
//        fileList.add(new FileExport("W3D#DesignMaterial_GetDesignMaterialListByCategoryIdWithRelation_v2.xls","W3D#DesignMaterial_GetDesignMaterialListByCategoryIdWithRelation_v2_result2003.xls"));
//        fileList.add(new FileExport("W3D#DesignMaterial_GetDesignMaterialListByCategoryIdWithRelationGoldenHome_v2.xls","W3D#DesignMaterial_GetDesignMaterialListByCategoryIdWithRelationGoldenHome_v2_result2003.xls"));
//        fileList.add(new FileExport("W3D#Favorite_Favories.xls","W3D#Favorite_Favories_result2003.xls"));
//        fileList.add(new FileExport("W3D#Production_GetEdgeListAndMaterialCodeByBaseMaterial.xls","W3D#Production_GetEdgeListAndMaterialCodeByBaseMaterial_result2003.xls"));
//
//        //1223 api接口
//        fileApiList.add(new FileExport("_api_sdapi_category_getCategoryListNew.xls","_api_sdapi_category_getCategoryListNew_result2003.xls"));
//        fileApiList.add(new FileExport("_api_sdapi_uc_getShopByDeptId.xls","_api_sdapi_uc_getShopByDeptId_result2003.xls"));
//        fileApiList.add(new FileExport("_api_sdapi_uploadchartlet_addChartlets.xls","_api_sdapi_uploadchartlet_addChartlets_result2003.xls"));
//        fileApiList.add(new FileExport("test.xls","test_result2003.xls"));

        //第三批接口basicapi接口
        fileApiList.add(new FileExport("_api_basicsdapi_homescheme_getHomeSchemes.xls","_api_basicsdapi_homescheme_getHomeSchemes_result2003.xls"));
//        fileApiList.add(new FileExport("_api_basicsdapi_homescheme_listByPage.xls","_api_basicsdapi_homescheme_listByPage_result2003.xls"));
//        fileApiList.add(new FileExport("_api_basicsdapi_materialaluminiumrelation_getdeptinfo.xls","_api_basicsdapi_materialaluminiumrelation_getdeptinfo_result2003.xls"));
//        fileApiList.add(new FileExport("_api_basicsdapi_shop_getShopByDept.xls","_api_basicsdapi_shop_getShopByDept_result2003.xls"));

        fileList.add(new FileExport("W3D#DesignMaterial_GetDesignMaterialListByCategoryIdForBrand_v2.xls","W3D#DesignMaterial_GetDesignMaterialListByCategoryIdForBrand_v2_r.xls"));

    }

}
