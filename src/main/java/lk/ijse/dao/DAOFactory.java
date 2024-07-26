package lk.ijse.dao;

import lk.ijse.dao.custom.impl.*;

public class DAOFactory {
    private static DAOFactory daoFactory;

    private DAOFactory(){

    }

    public static DAOFactory getDaoFactory(){
        return (daoFactory==null)? daoFactory=new DAOFactory() : daoFactory;
    }
    public enum DAOTypes{
        CUSTOMER,SUPPLIER,EMPLOYEE,ITEM,ORDER_DETAILS,ORDER,QUERY_DAO,USER
    }
    public SuperDAO getDAO(DAOTypes types){
       switch (types){
           case CUSTOMER :
               return  new CustomerDAOImpl();
           case EMPLOYEE:
               return new EmployeeDAOImpl();
           case SUPPLIER:
               return new SupplierDAOImpl();
           case ITEM:
               return new ItemDAOImpl();
           case USER:
               return new UserDAOImpl();
           case ORDER:
               return new OrderDAOImpl();
           case ORDER_DETAILS:
               return new OrderDetailDAOImpl();
           default:
               return null;
       }
    }
}
