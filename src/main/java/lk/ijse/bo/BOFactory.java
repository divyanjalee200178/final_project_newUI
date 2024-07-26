package lk.ijse.bo;

import lk.ijse.bo.custom.impl.*;

import javax.swing.plaf.PanelUI;

public class BOFactory {
    private static BOFactory boFactory;
    private BOFactory(){

    }
    public static BOFactory getBoFactory(){
        return (boFactory==null)? boFactory=new BOFactory() : boFactory;

    }
    public enum BOTypes{
        CUSTOMER,ITEM,SUPPLIER,EMPLOYEE,PLACE_ORDER,USER
    }
    public SuperBO getBO(BOTypes types){
        switch (types){
            case CUSTOMER:
                return  new CustomerBOImpl();
            case EMPLOYEE:
                return new EmployeeBOImpl();
            case SUPPLIER:
                return new SupplierBOImpl();
            case ITEM:
                return new ItemBOImpl();
            case USER:
                return new PasswordChangeBOImpl();
            case PLACE_ORDER:
                return new PlaceOrderBOImpl();
            default:
                return null;
        }
    }
}
