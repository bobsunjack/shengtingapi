import json

data={
    "name":"qianx",
    "age":10,
    "sex":"m"
}

json_str=json.dumps(data)

print(json_str)
