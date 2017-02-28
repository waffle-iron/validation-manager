package com.validation.manager.core.db;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Javier Ortiz Bultron <javier.ortiz.78@gmail.com>
 */
@Entity
@Table(name = "vm_id")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VmId.findAll",
            query = "SELECT v FROM VmId v")
    , @NamedQuery(name = "VmId.findById",
            query = "SELECT v FROM VmId v WHERE v.id = :id")
    , @NamedQuery(name = "VmId.findByTableName",
            query = "SELECT v FROM VmId v WHERE v.tableName = :tableName")
    , @NamedQuery(name = "VmId.findByLastId",
            query = "SELECT v FROM VmId v WHERE v.lastId = :lastId")})
public class VmId implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "table_name")
    private String tableName;
    @Basic(optional = false)
    @NotNull
    @Column(name = "last_id")
    private int lastId;

    public VmId() {
    }

    public VmId(String tableName, int lastId) {
        this.tableName = tableName;
        this.lastId = lastId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public int getLastId() {
        return lastId;
    }

    public void setLastId(int lastId) {
        this.lastId = lastId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof VmId)) {
            return false;
        }
        VmId other = (VmId) object;
        return !((this.id == null && other.id != null)
                || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "com.validation.manager.core.db.VmId[ id=" + id + " ]";
    }
}
