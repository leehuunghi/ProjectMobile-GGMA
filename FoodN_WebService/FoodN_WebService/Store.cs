using System;
using System.Collections.Generic;
using System.Data.Linq;
using System.Linq;
using System.Web;

namespace FoodN_WebService
{
    public class Store
    {
        string tenCuaHang;

        public string TenCuaHang
        {
            get { return tenCuaHang; }
            set { tenCuaHang = value; }
        }
        string diaChi;

        public string DiaChi
        {
            get { return diaChi; }
            set { diaChi = value; }
        }
        string gioDongCua;

        public string GioDongCua
        {
            get { return gioDongCua; }
            set { gioDongCua = value; }
        }
        string gioMoCua;

        public string GioMoCua
        {
            get { return gioMoCua; }
            set { gioMoCua = value; }
        }
        Byte[] hinhAnh;

        public Byte[] HinhAnh
        {
            get { return hinhAnh; }
            set { hinhAnh = value; }
        }
        string sdtCuaHang;

        public string SdtCuaHang
        {
            get { return sdtCuaHang; }
            set { sdtCuaHang = value; }
        }
    }
}