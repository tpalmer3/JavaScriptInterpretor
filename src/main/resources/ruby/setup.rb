require 'java'

java_import com.example.components.Console
java_import com.example.components.GitOps
java_import com.example.components.RedisOps
java_import com.example.components.SocketOps
java_import com.example.components.WebOps

console = Console.new()
git = GitOps.new()
redis = RedisOps.new()
sock = socket = SocketOps.new()
web = WebOps.new()
