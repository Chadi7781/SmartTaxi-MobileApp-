package dell_pc.example.com.smarttaxiriders.Model;

public class RidersInformation {

        private String email, fullName, mobile;
        private String gender, hireDate, image, city;
        private String typepermis;
        private boolean affecter;
        private int department;
        private String uid;
        private String firstName,lastName,password,address;


        public RidersInformation() {
        }

        public RidersInformation(String firstName, String lastName, String password, String address, String email) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.password = password;
            this.address = address;
            this.email = email;
        }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public RidersInformation(String email, String fullName, String mobile, String gender, String hireDate, String image, String city, String typepermis, boolean affecter, int department, String uid) {
            this.email = email;
            this.fullName = fullName;
            this.mobile = mobile;
            this.gender = gender;
            this.hireDate = hireDate;
            this.image = image;
            this.city = city;
            this.typepermis = typepermis;
            this.affecter = affecter;
            this.department = department;
            this.uid = uid;

        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getFullName() {
            return fullName;
        }

        public void setFullName(String fullName) {
            this.fullName = fullName;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getHireDate() {
            return hireDate;
        }

        public void setHireDate(String hireDate) {
            this.hireDate = hireDate;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getTypepermis() {
            return typepermis;
        }

        public void setTypepermis(String typepermis) {
            this.typepermis = typepermis;
        }

        public boolean isAffecter() {
            return affecter;
        }

        public void setAffecter(boolean affecter) {
            this.affecter = affecter;
        }

        public int getDepartment() {
            return department;
        }

        public void setDepartment(int department) {
            this.department = department;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

    }

