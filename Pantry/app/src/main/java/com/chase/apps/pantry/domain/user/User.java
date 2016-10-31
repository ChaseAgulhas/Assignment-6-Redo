package com.chase.apps.pantry.domain.user;

import java.io.Serializable;

/**
 * Created by Chase on 2016-09-21.
 */
public class User implements Serializable {

    private String name, surname;
    private String dateOfBirth;
    private String userID;
    private String email = "";

    private User(Builder builder)
    {
        this.name = builder.name;
        this.surname = builder.surname;
        this.dateOfBirth = builder.dateOfBirth;
        this.userID = builder.userID;
        this.email = builder.email;
    }

    public String getName()
    {
        return name;
    }


    public String getSurname()
    {
        return surname;
    }


    public String getDateOfBirth()
    {
        return dateOfBirth;
    }


    public String getUserID()
    {
        return userID;
    }


    public String getUserEmail()
    {
        return email;
    }

    public String toString(){
        return "UserID: " + getUserID() + "\nName: " + getName() + "\nSurname: " + getSurname() + "\nDate of birth: " + getDateOfBirth() + "\nEmail: " + getUserEmail();
    }

    public static class Builder
    {
        private String name, surname;
        private String dateOfBirth;
        private String userID;
        private String email;

        public Builder(){}

        public Builder userID(String value)
        {
            this.userID = value;
            return this;
        }

        public Builder name(String value)
        {
            this.name = value;
            return this;
        }

        public Builder surname(String value)
        {
            this.surname = value;
            return this;
        }

        public Builder dateOfBirth(String value)
        {
            this.dateOfBirth = value;
            return this;
        }

        public Builder email(String email)
        {
            this.email = email;
            return this;
        }

        public Builder copy(User value)
        {
            this.name = value.getName();
            this.surname = value.getSurname();
            this.dateOfBirth = value.getDateOfBirth();
            this.userID = value.getUserID();
            this.email = value.getUserEmail();

            return this;
        }

        public User build()
        {
            return new User(this);
        }
    }

    @Override
    public boolean equals(Object o)
    {
        if(this == o) return true;

        if(o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return userID != null ? userID.equals(user.userID) : user.userID == null;

    }

    @Override
    public int hashCode()
    {
        return userID != null ? userID.hashCode() : 0;
    }
}
