package com.sample.retailstore.constants;

/**
 * @author irfan.nagoo
 */
public interface MessageConstants {

    String LIST_SUCCESS_MSG = "Total Records [%d] Found";
    String GET_SUCCESS_MSG = "Record Retrieved Successfully";
    String SAVE_SUCCESS_MSG = "Record Saved Successfully";
    String ORDER_PLACED_MSG = "Order Placed Successfully";
    String ORDER_CANCELLED_MSG = "Order Cancelled Successfully";
    String ORDER_UPDATED_MSG = "Order Updated Successfully";
    String ORDER_NOT_FOUND = "Order Not Found";
    String ORDER_INVALID_QUANTITY_MSG = "Ordered Item quantity[%d] is greater than available quantity[%d]";
    String ORDER_INVALID_INPUT_MSG = "Requested Order Id[%d] or Status[%s] is Invalid";
    String ORDER_NOT_CANCELLED_MSG = "Current status of Order is [%s]. Order cannot be Cancelled";
    String VALIDATION_ERROR_MSG = "Validation Error has Occurred";
    String PROCESSING_ERROR_MSG = "Processing Error has Occurred";

}
