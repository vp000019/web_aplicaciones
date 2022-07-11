package ua.com.alevel.util;

import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.BaseEntity;
import ua.com.alevel.web.dto.response.PageData;
import ua.com.alevel.web.dto.response.ResponseDto;

    public final class WebResponseUtil {

    private WebResponseUtil() {
    }

        //Генерация класса для ответа в UI

    public static PageData<? extends ResponseDto> initPageData(
            DataTableResponse<? extends BaseEntity> tableResponse) {
        PageData<? extends ResponseDto> pageData = new PageData<>();
        pageData.setPageSize(tableResponse.getPageSize());
        pageData.setOrder(tableResponse.getOrder());
        pageData.setSort(tableResponse.getSort());
        pageData.setItemsSize(tableResponse.getItemsSize());
        pageData.initPaginationState();
        return pageData;
    }
}