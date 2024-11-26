﻿using LR.model;

namespace LR.repositories;

public interface IPurchasesRepository
{
    public Purchase Save(Purchase product);
    public Purchase? FindByNumber(int number);
    public List<Purchase> FindAll();
    public Purchase Detach(Purchase purchase);
}