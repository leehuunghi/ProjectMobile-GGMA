using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Web;
using System.Web.Services;

namespace FoodN_WebService
{
    /// <summary>
    /// Summary description for WebServiceFoodN
    /// </summary>
    [WebService(Namespace = "http://tempuri.org/")]
    [WebServiceBinding(ConformsTo = WsiProfiles.BasicProfile1_1)]
    [System.ComponentModel.ToolboxItem(false)]
    // To allow this Web Service to be called from script, using ASP.NET AJAX, uncomment the following line. 
    // [System.Web.Script.Services.ScriptService]
    public class WebServiceFoodN : System.Web.Services.WebService
    {

        private DataClassesFoodNDataContext db = new DataClassesFoodNDataContext();

        [WebMethod]
        public List<ViTri> GetListPointStore()
        {
            return db.ViTris.ToList();
        }

        [WebMethod(Description = "Kiểm tra tài khoản mật khẩu")]
        public bool login(string username, string password)
        {
                db.FoodN_Users.Single(u => u.SDT_User == username && u.MatKhau == password);
            return true;
        }

        [WebMethod(Description = "Đăng ký tài khoản mật khẩu")]
        public bool signUp(string username, string displayName, string password)
        {
            try
            {
                FoodN_User user = new FoodN_User();
                user.HoTen = displayName;
                user.SDT_User = username;
                user.MatKhau = password;
                db.FoodN_Users.InsertOnSubmit(user);
                db.SubmitChanges();
            }
            catch
            {
                return false;
            }
            return true;
        }
    }
}
