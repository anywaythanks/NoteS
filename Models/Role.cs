using System.ComponentModel.DataAnnotations.Schema;
using NpgsqlTypes;

namespace LR.model;
public enum Role
{
    [PgName("salesman")]
    salesman, //write product
    [PgName("buyer")]
    buyer
}