using ItemAPI.Data;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using ProductsAPI;

namespace ItemAPI.Controllers
{
  [Route("api/[controller]")]
  [ApiController]
  public class itemController : ControllerBase
  {
    private readonly DataContext _context;
    public itemController(DataContext context)
    {
      _context = context;
    }

    [HttpGet]
    public async Task<ActionResult<List<Item>>> GetItems()
    {
      return Ok(await _context.Items.ToListAsync());
    }
  }
}
