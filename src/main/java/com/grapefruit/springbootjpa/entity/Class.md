
临时代码
```java
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "t_student_class",
            joinColumns = {@JoinColumn(name = "c_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "s_id", referencedColumnName = "id")})
    List<Student> studentList;

    public Class(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Class(int id) {
        this.id = id;
    }
```
