using LR.model.dto.product;
using LR.services;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;

namespace LR.controllers;

[ApiController]
[Route("api")]
public class ProductController(
    ProductRegisterService productRegisterService,
    ProductSellerService productSellerService,
    ProductDeleteService productDeleteService,
    ProductInformationService productInformationService)
    : Controller
{
    //PUT /accounts/{name}/product/{productName}
    [HttpPut]
    [Authorize(Roles = "salesman")]
    [Route("accounts/{accountName}/product/{productName}/")]
    public IResult Add([FromRoute] string accountName, [FromRoute] string productName,
        [FromBody] ProductRegisterDto registerDto)
    {
        return accountName != User.Identity?.Name
           ? Results.Unauthorized()
           : Results.Json(productRegisterService.Register(accountName, productName, registerDto));
    }

    public class QDTO
    {
        public int Quantity { get; set; }

        public QDTO()
        {
        }

        public QDTO(int quantity)
        {
            Quantity = quantity;
        }
    }
    [HttpPost]
    [Authorize(Roles = "buyer")]
    [Route("accounts/{accountName}/product/{productName}/purchase/")]
    public IResult Purchase([FromRoute] string accountName, [FromRoute] string productName,
        [FromBody] QDTO quantity)
    {
        return accountName != User.Identity?.Name
            ? Results.Unauthorized()
            : Results.Json(productSellerService.Buy(accountName, productName, quantity.Quantity));
    }

    [HttpPost]
    [Authorize(Roles = "salesman")]
    [Route("accounts/{accountName}/product/{productName}/replenish/")]
    public IResult Replenish([FromRoute] string accountName, [FromRoute] string productName, int quantity)
    {
        return accountName != User.Identity?.Name
            ? Results.Unauthorized()
            : Results.Json(productSellerService.Replenish(accountName, productName, quantity));
    }

    [HttpDelete]
    [Authorize(Roles = "salesman")]
    [Route("accounts/{accountName}/product/{productName}/")]
    public IResult Delete([FromRoute] string accountName, [FromRoute] string productName)
    {
        return accountName != User.Identity?.Name
            ? Results.Unauthorized()
            : Results.Json(productDeleteService.Delete(accountName, productName));
    }

    [HttpGet]
    [Authorize]
    [Route("products")]
    public List<ProductPartialDto> Products()
    {
        return productInformationService.GetPartials();
    }
}