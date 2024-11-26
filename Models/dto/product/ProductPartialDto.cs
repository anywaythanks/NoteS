﻿using LR.model.dto.user;

namespace LR.model.dto.product;

public class ProductPartialDto(string name, string visibleName, string description, int quantity, Decimal cost, AccountPartialDto owner)
{
    public string Name { get; } = name;
    public AccountPartialDto Owner { get; } = owner;
    public string VisibleName { get; } = visibleName;
    public string Description { get; } = description;
    public int Quantity { get; set; } = quantity;
    public Decimal Cost { get; set; } = cost;
}