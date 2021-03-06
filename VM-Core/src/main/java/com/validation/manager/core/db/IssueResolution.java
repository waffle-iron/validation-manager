/* 
 * Copyright 2017 Javier A. Ortiz Bultron javier.ortiz.78@gmail.com.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.validation.manager.core.db;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 *
 * @author Javier A. Ortiz Bultron javier.ortiz.78@gmail.com
 */
@Entity
@Table(name = "issue_resolution")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "IssueResolution.findAll",
            query = "SELECT i FROM IssueResolution i")
    , @NamedQuery(name = "IssueResolution.findById",
            query = "SELECT i FROM IssueResolution i WHERE i.id = :id")
    , @NamedQuery(name = "IssueResolution.findByName",
            query = "SELECT i FROM IssueResolution i WHERE i.name = :name")})
public class IssueResolution implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.TABLE,
            generator = "ISSUE_RESOLUTION_IDGEN")
    @TableGenerator(name = "ISSUE_RESOLUTION_IDGEN", table = "vm_id",
            pkColumnName = "table_name",
            valueColumnName = "last_id",
            pkColumnValue = "issue_resolution",
            initialValue = 1,
            allocationSize = 1)
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "name")
    private String name;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "issueResolutionId")
    private List<Issue> issueList;

    public IssueResolution() {
    }

    public IssueResolution(Integer id) {
        this.id = id;
    }

    public IssueResolution(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlTransient
    @JsonIgnore
    public List<Issue> getIssueList() {
        return issueList;
    }

    public void setIssueList(List<Issue> issueList) {
        this.issueList = issueList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        
        if (!(object instanceof IssueResolution)) {
            return false;
        }
        IssueResolution other = (IssueResolution) object;
        return this.id.equals(other.id);
    }

    @Override
    public String toString() {
        return "com.validation.manager.core.db.IssueResolution[ id=" + id + " ]";
    }
}
