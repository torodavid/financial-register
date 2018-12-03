package com.torodavid.thesis.financialregister.dal.model;

import com.torodavid.thesis.financialregister.dal.enums.Category;
import com.torodavid.thesis.financialregister.dal.enums.FlowDirection;
import com.torodavid.thesis.financialregister.dal.enums.Priority;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
public class CashFlow {

    @Id
    @Column(length = 40)
    private String id;
    @NotNull
    @NotEmpty
    @Size(min=3, max=20)
    private String name;
    private String description;
    private int amount;
    private LocalDateTime creationDate;
    private LocalDateTime modificationDate;
    private Priority priority;
    private Category category;
    private FlowDirection flowDirection;
    /**
     * invalid kulso kulcsokat ignoralja az EntityManager, ha admin akarna rakeresni osszes cashFlowra
     */
    @NotFound(action=NotFoundAction.IGNORE)
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public CashFlow() {
    }

    public CashFlow(String id, String name, String description, int amount, LocalDateTime creationDate, LocalDateTime modificationDate, Priority priority, Category category, FlowDirection flowDirection, User user) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.amount = amount;
        this.creationDate = creationDate;
        this.modificationDate = modificationDate;
        this.priority = priority;
        this.category = category;
        this.flowDirection = flowDirection;
        this.user = user;
    }

    public String getId() {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDateTime getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(LocalDateTime modificationDate) {
        this.modificationDate = modificationDate;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public FlowDirection getFlowDirection() {
        return flowDirection;
    }

    public void setFlowDirection(FlowDirection flowDirection) {
        this.flowDirection = flowDirection;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "CashFlow{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", amount=" + amount +
                ", creationDate=" + creationDate +
                ", modificationDate=" + modificationDate +
                ", priority=" + priority +
                ", category=" + category +
                ", flowDirection=" + flowDirection +
                ", user=" + user +
                '}';
    }
}
