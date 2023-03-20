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

    [HttpPost]
    public async Task<ActionResult<List<Item>>> CreateItem(Item item)
    {
      _context.Items.Add(item);
      await _context.SaveChangesAsync();

      return Ok(await _context.Items.ToListAsync());
    }

    [HttpPut]
    public async Task<ActionResult<List<Item>>> UpdateItem(Item item)
    {
      var dbItem = await _context.Items.FindAsync(item.id);
      if (dbItem == null)
        return BadRequest("Item not found.");

      dbItem.id = item.id;
      dbItem.name = item.name;
      dbItem.code = item.code;
      dbItem.brand = item.brand;
      dbItem.unitprice = item.unitprice;

      await _context.SaveChangesAsync();

      return Ok(await _context.Items.ToListAsync());

    }

    [HttpDelete("{id}")]
    public async Task<ActionResult<List<Item>>> DeleteItem(int id)
    {
      var dbItem = await _context.Items.FindAsync(id);
      if (dbItem == null)
        return BadRequest("Item not found.");

      _context.Items.Remove(dbItem);
      await _context.SaveChangesAsync();

      return Ok(await _context.Items.ToListAsync());
    }
  }
}
