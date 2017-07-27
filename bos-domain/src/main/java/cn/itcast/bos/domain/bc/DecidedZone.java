package cn.itcast.bos.domain.bc;
// Generated 2017-7-18 17:02:24 by Hibernate Tools 3.2.2.GA


import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * DecidedZone generated by hbm2java
 */
@Entity
@Table(name = "bc_decidedzone"
        , catalog = "mavenbos"
)
public class DecidedZone implements java.io.Serializable {


    private String id;
    private Staff staff;
    private String name;
    private Set<Subarea> subareas = new HashSet<Subarea>(0);

    public DecidedZone() {
    }

    public DecidedZone(Staff staff, String name, Set<Subarea> subareas) {
        this.staff = staff;
        this.name = name;
        this.subareas = subareas;
    }

    @Id

    @Column(name = "ID", unique = true, nullable = false, length = 32)
    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "STAFF_ID")
    public Staff getStaff() {
        return this.staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    @Column(name = "NAME", length = 30)
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "decidedZone")
    @JSONField(serialize = false)
    public Set<Subarea> getSubareas() {
        return this.subareas;
    }

    public void setSubareas(Set<Subarea> subareas) {
        this.subareas = subareas;
    }


}

