using System;
using System.IO;

namespace Sourceworx.DMS.NativeApp.Models
{
    public class DocumentModel
    {
        public string FilePath { get; set; }
        public string FileName => Path.GetFileName(FilePath);
        public DateTime CreatedDate => File.GetCreationTime(FilePath);
        
        public DocumentModel(string filePath)
        {
            FilePath = filePath;
        }
    }
}
