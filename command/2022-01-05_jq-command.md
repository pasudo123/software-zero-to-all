## awk 👀
* 경량화된 json processor 커맨드
* curl 과 함께 사용할 수 있다.

## 예시 1
```json
{
  "name": "jq",
  "full_name": "jq",
  "tap": "homebrew/core",
  "oldname": null,
  "aliases": [

  ],
  "versioned_formulae": [

  ],
  "desc": "Lightweight and flexible command-line JSON processor",
  "license": "MIT",
  "homepage": "https://stedolan.github.io/jq/",
  "versions": {
    "stable": "1.6",
    "head": "HEAD",
    "bottle": true
  },
  "urls": {
    "stable": {
      "url": "https://github.com/stedolan/jq/releases/download/jq-1.6/jq-1.6.tar.gz",
      "tag": null,
      "revision": null
    }
  },
  "revision": 0,
  "version_scheme": 0,
  "bottle": {
    "stable": {
      "rebuild": 1,
      "root_url": "https://ghcr.io/v2/homebrew/core",
      "files": {
        "arm64_monterey": {
          "cellar": ":any",
          "url": "https://ghcr.io/v2/homebrew/core/jq/blobs/sha256:f70e1ae8df182b242ca004492cc0a664e2a8195e2e46f30546fe78e265d5eb87",
          "sha256": "f70e1ae8df182b242ca004492cc0a664e2a8195e2e46f30546fe78e265d5eb87"
        },
        "arm64_big_sur": {
          "cellar": ":any",
          "url": "https://ghcr.io/v2/homebrew/core/jq/blobs/sha256:674b3ae41c399f1e8e44c271b0e6909babff9fcd2e04a2127d25e2407ea4dd33",
          "sha256": "674b3ae41c399f1e8e44c271b0e6909babff9fcd2e04a2127d25e2407ea4dd33"
        },
        "monterey": {
          "cellar": ":any",
          "url": "https://ghcr.io/v2/homebrew/core/jq/blobs/sha256:7fee6ea327062b37d34ef5346a84810a1752cc7146fff1223fab76c9b45686e0",
          "sha256": "7fee6ea327062b37d34ef5346a84810a1752cc7146fff1223fab76c9b45686e0"
        },
        "big_sur": {
          "cellar": ":any",
          "url": "https://ghcr.io/v2/homebrew/core/jq/blobs/sha256:bf0f8577632af7b878b6425476f5b1ab9c3bf66d65affb0c455048a173a0b6bf",
          "sha256": "bf0f8577632af7b878b6425476f5b1ab9c3bf66d65affb0c455048a173a0b6bf"
        },
        "catalina": {
          "cellar": ":any",
          "url": "https://ghcr.io/v2/homebrew/core/jq/blobs/sha256:820a3c85fcbb63088b160c7edf125d7e55fc2c5c1d51569304499c9cc4b89ce8",
          "sha256": "820a3c85fcbb63088b160c7edf125d7e55fc2c5c1d51569304499c9cc4b89ce8"
        },
        "mojave": {
          "cellar": ":any",
          "url": "https://ghcr.io/v2/homebrew/core/jq/blobs/sha256:71f0e76c5b22e5088426c971d5e795fe67abee7af6c2c4ae0cf4c0eb98ed21ff",
          "sha256": "71f0e76c5b22e5088426c971d5e795fe67abee7af6c2c4ae0cf4c0eb98ed21ff"
        },
        "high_sierra": {
          "cellar": ":any",
          "url": "https://ghcr.io/v2/homebrew/core/jq/blobs/sha256:dffcffa4ea13e8f0f2b45c5121e529077e135ae9a47254c32182231662ee9b72",
          "sha256": "dffcffa4ea13e8f0f2b45c5121e529077e135ae9a47254c32182231662ee9b72"
        },
        "sierra": {
          "cellar": ":any",
          "url": "https://ghcr.io/v2/homebrew/core/jq/blobs/sha256:bb4d19dc026c2d72c53eed78eaa0ab982e9fcad2cd2acc6d13e7a12ff658e877",
          "sha256": "bb4d19dc026c2d72c53eed78eaa0ab982e9fcad2cd2acc6d13e7a12ff658e877"
        },
        "x86_64_linux": {
          "cellar": ":any_skip_relocation",
          "url": "https://ghcr.io/v2/homebrew/core/jq/blobs/sha256:2beea2c2c372ccf1081e9a5233fc3020470803254284aeecc071249d76b62338",
          "sha256": "2beea2c2c372ccf1081e9a5233fc3020470803254284aeecc071249d76b62338"
        }
      }
    }
  },
  "keg_only": false,
  "keg_only_reason": null,
  "bottle_disabled": false,
  "options": [

  ],
  "build_dependencies": [

  ],
  "dependencies": [
    "oniguruma"
  ],
  "recommended_dependencies": [

  ],
  "optional_dependencies": [

  ],
  "uses_from_macos": [

  ],
  "requirements": [

  ],
  "conflicts_with": [

  ],
  "caveats": null,
  "installed": [
    {
      "version": "1.6",
      "used_options": [

      ],
      "built_as_bottle": true,
      "poured_from_bottle": true,
      "runtime_dependencies": [
        {
          "full_name": "oniguruma",
          "version": "6.9.7.1",
          "declared_directly": true
        }
      ],
      "installed_as_dependency": false,
      "installed_on_request": true
    }
  ],
  "linked_keg": "1.6",
  "pinned": false,
  "outdated": false,
  "deprecated": false,
  "deprecation_date": null,
  "deprecation_reason": null,
  "disabled": false,
  "disable_date": null,
  "disable_reason": null,
  "analytics": {
    "install": {
      "30d": {
        "jq": 43543,
        "jq --HEAD": 17
      },
      "90d": {
        "jq": 143297,
        "jq --HEAD": 77
      },
      "365d": {
        "jq": 554438,
        "jq --HEAD": 473
      }
    },
    "install_on_request": {
      "30d": {
        "jq": 42343,
        "jq --HEAD": 17
      },
      "90d": {
        "jq": 139285,
        "jq --HEAD": 77
      },
      "365d": {
        "jq": 539971,
        "jq --HEAD": 474
      }
    },
    "build_error": {
      "30d": {
        "jq": 1
      }
    }
  },
  "analytics-linux": {
    "install": {
      "30d": {
        "jq": 2051,
        "jq --HEAD": 2
      },
      "90d": {
        "jq": 5546,
        "jq --HEAD": 2
      },
      "365d": {
        "jq": 18860,
        "jq --HEAD": 6
      }
    },
    "install_on_request": {
      "30d": {
        "jq": 1932,
        "jq --HEAD": 2
      },
      "90d": {
        "jq": 5275,
        "jq --HEAD": 2
      },
      "365d": {
        "jq": 17926,
        "jq --HEAD": 6
      }
    },
    "build_error": {
      "30d": {
        "jq": 0
      }
    }
  },
  "generated_date": "2022-01-05"
}
```

## 출력을 JSON 형태로 나타내기
```json
$ curl https://formulae.brew.sh/api/formula/jq.json | jq '.'
```

## 출력에 대한 필드값을 배열로 나타내기
```json
$ curl https://formulae.brew.sh/api/formula/jq.json | jq '[.name, .full_name, .tap, .oldname]'
[
"jq",
"jq",
"homebrew/core",
null
]
```

## 출력에 대한 필드값을 새로운 오브젝트로 나타내기
```json
$ curl https://formulae.brew.sh/api/formula/jq.json | jq '{name: .full_name, tap: .tap,  oldName: .oldname}'
{
  "name": "jq",
  "tap": "homebrew/core",
  "oldName": null
}
```

## 출력에 대한 필드값을 csv 형태로 나타내기
```json
$ curl https://formulae.brew.sh/api/formula/jq.json | jq '[.name, .full_name, .tap, .desc] | @csv'
"\"jq\",\"jq\",\"homebrew/core\",\"Lightweight and flexible command-line JSON processor\""
        
// -r 옵션을 주면, raw 형태로 따옴표 없이 출력된다.
$ curl https://formulae.brew.sh/api/formula/jq.json | jq -r '[.name, .full_name, .tap, .desc] | @csv'
"jq","jq","homebrew/core","Lightweight and flexible command-line JSON processor"
```

## 특정 필드값 기준으로 추출하기
```json
$ curl https://formulae.brew.sh/api/formula/jq.json | jq '.bottle'

{
  "stable": {
    "rebuild": 1,
    "root_url": "https://ghcr.io/v2/homebrew/core",
    "files": {
      "arm64_monterey": {
        "cellar": ":any",
        "url": "https://ghcr.io/v2/homebrew/core/jq/blobs/sha256:f70e1ae8df182b242ca004492cc0a664e2a8195e2e46f30546fe78e265d5eb87",
        "sha256": "f70e1ae8df182b242ca004492cc0a664e2a8195e2e46f30546fe78e265d5eb87"
      },
      "arm64_big_sur": {
        "cellar": ":any",
        "url": "https://ghcr.io/v2/homebrew/core/jq/blobs/sha256:674b3ae41c399f1e8e44c271b0e6909babff9fcd2e04a2127d25e2407ea4dd33",
        "sha256": "674b3ae41c399f1e8e44c271b0e6909babff9fcd2e04a2127d25e2407ea4dd33"
      },
      "monterey": {
        "cellar": ":any",
        "url": "https://ghcr.io/v2/homebrew/core/jq/blobs/sha256:7fee6ea327062b37d34ef5346a84810a1752cc7146fff1223fab76c9b45686e0",
        "sha256": "7fee6ea327062b37d34ef5346a84810a1752cc7146fff1223fab76c9b45686e0"
      },
      "big_sur": {
        "cellar": ":any",
        "url": "https://ghcr.io/v2/homebrew/core/jq/blobs/sha256:bf0f8577632af7b878b6425476f5b1ab9c3bf66d65affb0c455048a173a0b6bf",
        "sha256": "bf0f8577632af7b878b6425476f5b1ab9c3bf66d65affb0c455048a173a0b6bf"
      },
      "catalina": {
        "cellar": ":any",
        "url": "https://ghcr.io/v2/homebrew/core/jq/blobs/sha256:820a3c85fcbb63088b160c7edf125d7e55fc2c5c1d51569304499c9cc4b89ce8",
        "sha256": "820a3c85fcbb63088b160c7edf125d7e55fc2c5c1d51569304499c9cc4b89ce8"
      },
      "mojave": {
        "cellar": ":any",
        "url": "https://ghcr.io/v2/homebrew/core/jq/blobs/sha256:71f0e76c5b22e5088426c971d5e795fe67abee7af6c2c4ae0cf4c0eb98ed21ff",
        "sha256": "71f0e76c5b22e5088426c971d5e795fe67abee7af6c2c4ae0cf4c0eb98ed21ff"
      },
      "high_sierra": {
        "cellar": ":any",
        "url": "https://ghcr.io/v2/homebrew/core/jq/blobs/sha256:dffcffa4ea13e8f0f2b45c5121e529077e135ae9a47254c32182231662ee9b72",
        "sha256": "dffcffa4ea13e8f0f2b45c5121e529077e135ae9a47254c32182231662ee9b72"
      },
      "sierra": {
        "cellar": ":any",
        "url": "https://ghcr.io/v2/homebrew/core/jq/blobs/sha256:bb4d19dc026c2d72c53eed78eaa0ab982e9fcad2cd2acc6d13e7a12ff658e877",
        "sha256": "bb4d19dc026c2d72c53eed78eaa0ab982e9fcad2cd2acc6d13e7a12ff658e877"
      },
      "x86_64_linux": {
        "cellar": ":any_skip_relocation",
        "url": "https://ghcr.io/v2/homebrew/core/jq/blobs/sha256:2beea2c2c372ccf1081e9a5233fc3020470803254284aeecc071249d76b62338",
        "sha256": "2beea2c2c372ccf1081e9a5233fc3020470803254284aeecc071249d76b62338"
      }
    }
  }
}
```

## 오브젝트 내 뎁스를 깊이해서 갈 수 있다.
```json
$ curl https://formulae.brew.sh/api/formula/jq.json | jq '.bottle.stable.root_url'

"https://ghcr.io/v2/homebrew/core"
```

# <a id="reference"></a> reference 🚀
https://formulae.brew.sh/formula/jq