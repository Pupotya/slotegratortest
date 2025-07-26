package org.slotegrator.user.api;

public class LoginResponseBody {

    public User user;
    public String accessToken;

    public static class User {

        public String id;
        public String email;
        public String name;
        public String surname;
        public String role;
        public String position;
        public String status;
        public Boolean isReport;
        public String comment;
        public String createBy;
        public String report;
        public String updated_at;
        public String created_at;
        public Boolean feedback;
        public String finished_at;
        public String linkedin;
        public String city;
        public String jira;

    }

}
