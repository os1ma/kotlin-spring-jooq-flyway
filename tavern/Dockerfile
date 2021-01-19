FROM python:3.8.1-alpine3.11

# pykwalify のバージョンを固定している理由は以下のページの通り
# https://gitmemory.com/issue/taverntesting/tavern/631/756102624
RUN pip install tavern==0.34.0 pykwalify==1.7

CMD ["py.test"]
