package com.example.todotask;

public class Todo implements  Comparable<Todo> {
    String id;
    String userName;
    String decrption ;
    String name ;
    String date ;

    public Todo(String id, String userName, String decrption, String name, String date) {
        this.id = id;
        this.userName = userName;
        this.decrption = decrption;
        this.name = name;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDecrption() {
        return decrption;
    }

    public void setDecrption(String decrption) {
        this.decrption = decrption;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Todo{" +
                "id='" + id + '\'' +
                ", userName='" + userName + '\'' +
                ", decrption='" + decrption + '\'' +
                ", name='" + name + '\'' +
                ", date='" + date + '\'' +
                '}';
    }

    @Override
    public int compareTo(Todo todo) {
        return this.getDate().compareTo(todo.getDate());
    }
}
