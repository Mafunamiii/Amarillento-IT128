using BlogDataLibrary.Database;
using BlogDataLibrary.Data;
using Microsoft.Extensions.Configuration;
using BlogDataLibrary.Models;

namespace BlogTestUI;
class Program
{

    static void Main(string[] args)
    {
        
        bool repeat = true;
        int MenuInput = 0;
        bool loggedIn = false;
        do {
            
            SqlData db = GetConnection();
            
            
            try
            {
                int tempMenuInputint = 0;

                Console.Write(
                "\tC# .NET 6 Class libraries demonstration, please type the number based on the given menu [1-6]\n" +
                "[1] Login\n" +
                "[2] Register\n" +
                "[3] Add post\n" +
                "[4] Show Posts\n" +
                "[5] Show Detailed post\n" +
                "[6] Exit\n" +
                "-------------\n" +
                "Enter menu number: "
                );


                string tempMenuInputstr = Console.ReadLine();
                tempMenuInputint = int.Parse(tempMenuInputstr);

                if (tempMenuInputint < 1 || tempMenuInputint > 6)
                {
                    throw new Exception();
                }

                MenuInput = tempMenuInputint;

            }
            catch (Exception ex)
            {
                Console.Clear();
                Console.WriteLine("Wrong value entered, please try again");
                
            }

            switch (MenuInput)
            {
                case 1:
                    Console.Clear();
                    Authenticate(db);
                    Console.WriteLine("====================\n");
                    loggedIn = true;

                    break;
                case 2:
                    Register(db);
                    Console.Clear();
                    Console.WriteLine("Sucessfully Registered!");
                    break;
                case 3:
                   
                    if (loggedIn = false)
                    {
                        Authenticate(db);
                        loggedIn = true;
                    }
                    else 
                    {
                        AddPost(db);
                    }
                    Console.Clear();
                    break;
                case 4:
                    if (loggedIn = false)
                    {
                        Authenticate(db);
                        loggedIn = true;
                    }
                    else
                    {
                        ListPosts(db);
                    }
                    break;
                case 5:
                    if (loggedIn = false)
                    {
                        Authenticate(db);
                        loggedIn = true;
                    }
                    else
                    {
                        ShowPostDetails(db);
                    }
                    break;
                case 6:
                    Console.WriteLine("Exiting program");
                    repeat = false;
                    break;
                default:
                    Console.WriteLine();
                    break;

            }
        }
        
        while (repeat) ;
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
        }
        else
        {
            Console.WriteLine($"Welcome, {user.UserName}");
        }
    }

    public static void Register(SqlData db)
    {
        Console.Write("Enter new username: ");
        var username = Console.ReadLine();

        Console.Write("Enter new password: ");
        var password = Console.ReadLine();

        Console.Write("Enter first name: ");
        var firstName = Console.ReadLine();

        Console.Write("Enter last name: ");
        var lastName = Console.ReadLine();

        db.Register(username, firstName, lastName, password);

    }

    private static void AddPost(SqlData db)
    {
        UserModel user = GetCurrentUser(db);

        Console.Write("Title: ");
        string title = Console.ReadLine();

        Console.WriteLine("Write body: ");
        string body = Console.ReadLine();

        PostModel post = new PostModel
        {
            Title = title,
            Body = body,
            DateCreated = DateTime.Now,
            UserId = user.Id
        };

        db.AddPost(post);
    }

    private static void ListPosts(SqlData db)
    {
        List<ListPostModel> posts = db.ListPosts();

        foreach (ListPostModel post in posts)
        {
            Console.WriteLine($"{post.Id}. Title: {post.Title} by {post.UserName} [{post.DateCreated.ToString("yyyy-MM-dd")}");
            Console.WriteLine($"{post.Body.Substring(0, 20)}...\n");

        }
    }

    private static void ShowPostDetails(SqlData db)
    {
        Console.Write("Enter a post ID: ");
        int id = Int32.Parse(Console.ReadLine());

        ListPostModel post = db.ShowPostDetails(id);
        Console.WriteLine(post.Title);
        Console.WriteLine($"by {post.FirstName} {post.LastName} [{post.UserName}]\n");

        Console.WriteLine(post.Body);
        Console.WriteLine(post.DateCreated.ToString("MMM d yyyy"));


    }

}




