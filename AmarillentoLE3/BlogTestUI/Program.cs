using BlogDataLibrary.Database;
using BlogDataLibrary.Data;
using Microsoft.Extensions.Configuration;
using BlogDataLibrary.Models;

namespace BlogTestUI;
class Program
{

    static void Main(string[] args)
    {
        SqlData db = GetConnection();

        Authenticate(db);

        Console.WriteLine("Press Enter to exit...");
        Console.ReadLine();
    }

    static SqlData GetConnection()
    {
        var builder = new ConfigurationBuilder()
            .SetBasePath(Directory.GetCurrentDirectory())
            .AddJsonFile("appsettings.json");

        IConfiguration config = builder.Build();
        ISqlDataAccess dbAccess = new SqlDataAccess(config);
        SqlData db = new SqlData(dbAccess);

        return db;
    }

    private static UserModel GetCurrentUser(SqlData db)
    {
        Console.Write("Username: ");
        string username = Console.ReadLine();

        Console.Write("Password: ");
        string password = Console.ReadLine();

        UserModel user = db.Authenticate(username, password);

        return user;
    }

    public static void Authenticate(SqlData db)
    {
        UserModel user = GetCurrentUser(db);

        if (user == null)
        {
            Console.WriteLine("Invalid credentials.");
        } else
        {
            Console.WriteLine($"Welcome, {user.UserName}");
        }
    }
}

