local list = {1,2,3,4,5,6,7,7,8,99,7,54,3,5,6};

local function get(l) do
	return list[l] or 0;
end end

for k, i in ipairs(list) do
	print(k..":   "..i.."->"..get(k+1))
end

return 93284