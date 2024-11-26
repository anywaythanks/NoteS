using LR.model.dto.purchases;
using LR.services;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;

namespace LR.controllers;

[Route("api")]
[ApiController]
public class PurchasesController(PurchasesInformationService purchasesInformationService) : Controller
{
    [HttpGet]
    [AllowAnonymous]
    [Route("purchases")]
    public List<PurchasePartialDto> Purchases()
    {
        return purchasesInformationService.GetAll();
    }
}