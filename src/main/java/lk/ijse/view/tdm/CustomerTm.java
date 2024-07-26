package lk.ijse.view.tdm;


public class CustomerTm implements Comparable<CustomerTm>{
    private String id;
    private String name;
    private String address;
    private String email;
    private String tel;

    public CustomerTm() {
    }

    public CustomerTm(String id, String name, String address, String email, String tel) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.email = email;
        this.tel = tel;
    }

    public String getId() {
        //System.out.println(id);
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    @Override
    public String toString() {
        return "CustomerTm{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", tel='" + tel + '\'' +
                '}';
    }

    @Override
    public int compareTo(CustomerTm c) {
        if (id == null) {
            return (c.getId() == null) ? 0 : -1;
        }
        return id.compareTo(c.getId());
    }



}
