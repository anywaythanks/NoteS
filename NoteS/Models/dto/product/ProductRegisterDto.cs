using System.ComponentModel.DataAnnotations;

namespace LR.model.dto.product;

public class ProductRegisterDto(string visibleName, string description,int cost)
{
    [Required]
    [MaxLength(64)]
    [MinLength(3)]
    public string VisibleName { get; } = visibleName;
    [Required]
    [MaxLength(64)]
    [MinLength(3)]
    public string Description { get; } = description;
    [Required]
    [Range(0, 100000)]
    public int Cost { get; } = cost;
}