using System.Text.Json.Serialization;

namespace Sourceworx.DMS.NativeApp.Models
{
    public class ProjectTask
    {
        public int ID { get; set; }
        public string Title { get; set; } = string.Empty;
        public bool IsCompleted { get; set; }

        [JsonIgnore]
        public int ProjectID { get; set; }

        // Document related properties
        public string DocumentPath { get; set; } = string.Empty;
        public bool HasDocument => !string.IsNullOrEmpty(DocumentPath);
    }
}