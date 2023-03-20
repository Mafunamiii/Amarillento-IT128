using Microsoft.EntityFrameworkCore;
using ProductsAPI;

namespace ItemAPI.Data
{
  public class DataContext : DbContext
  {
    public DataContext(DbContextOptions<DataContext>options) : base(options) { }
    public DbSet<Item> Items => Set<Item>();
  }
}
