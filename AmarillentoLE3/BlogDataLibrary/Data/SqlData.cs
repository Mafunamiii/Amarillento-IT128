using BlogDataLibrary.Database;
using BlogDataLibrary.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.CompilerServices;
using System.Text;
using System.Threading.Tasks;

[assembly: InternalsVisibleTo("BlogTestUI")]

namespace BlogDataLibrary.Data
{
    internal class SqlData
    {
        private ISqlDataAccess _db;
        private const string connectionStringName = "SqlDb";

        public SqlData(ISqlDataAccess db)
        {
            _db = db;
        }

        public UserModel Authenticate(string username, string password)
        {
            
            UserModel result = _db.LoadData<UserModel, dynamic>("BlogDB.dbo.spUsers_Authenticate", new { username, password }, connectionStringName, true).FirstOrDefault();
            
            return result;
        }
    }
}
