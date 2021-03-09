# Training repository for students of 2020

[![][super_linter img]][super_linter]
[![][codacy img]][codacy]
[![][codefactor img]][codefactor]
[![][depshield img]][depshield]
[![][snyk img]][snyk]
[![][dependabot img]][dependabot]
[![][travisci img]][travisci]
[![][sonar img]][sonar]
[![][bch img]][bch]

## Rules

### Commit message rules: [How to][conventional_commits]

#### Basic:

+ `fix: {message}` - For fixes.
+ `bug: {message}` - For fixes.
+ `feature: {message}` - For new features.
+ `feat: {message}` - For new features.
+ `doc: {message}` - For documentation updates.
+ `documentation: {message}` - For documentation updates.
+ `ci: {message}` - For CI/CD changes.

#### Checks:

+ Pull requests are validated by GH checks.

### Code Style: [Google Style][google_style]

#### Basic:

+ ***file charset*** = `utf-8`
+ ***end of line format*** = `lf`
+ ***indent size*** = `2`
+ ***indent style*** = `space`
+ ***max line length*** = `120`
+ ***tab width*** = `2`
+ ***continuation indent size*** = `4`
+ Rest: ***checkstyle.xml*** config file;

#### Checks:

+ Formatting automatically covered in IDEs via ***EditorConfig*** plugin. Covered by `.editorconfig` settings file in
  the repo.
+ Checkstyle maven plugin with `Google Style` configuration.  
+ Pull requests are validated by GH checks (by running `checkstyle`). 
+ ***Restyled*** `bot` automatically creates Pull request with fixes for the corresponding branch on each pull request.

## Documentation

+ [Documentation Site(API)][gh_docs]
+ [Google Drive][gd_docs]

### Test stand: ###

+ [Discovery Service][heroku_discovery]
+ [Config Service][heroku_config]
+ [Tax Service][heroku_tax]
+ [Bank Service][heroku_bank]
+ [Communal Service][heroku_communal]
+ [Logger][heroku_logger]

[super_linter]: https://github.com/marketplace/actions/super-linter

[super_linter img]: https://github.com/kostua16/UNC_2020_MS_TLT/workflows/Lint%20Code%20Base/badge.svg

[codacy]: https://www.codacy.com/gh/kostua16/UNC_2020_MS_TLT/dashboard?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=kostua16/UNC_2020_MS_TLT&amp;utm_campaign=Badge_Grade

[codacy img]: https://app.codacy.com/project/badge/Grade/feaf6ddb9de643f2b4b8afa969775b9a

[codefactor]: https://www.codefactor.io/repository/github/kostua16/unc_2020_ms_tlt

[codefactor img]: https://www.codefactor.io/repository/github/kostua16/unc_2020_ms_tlt/badge

[depshield]: https://depshield.github.io

[depshield img]: https://depshield.sonatype.org/badges/kostua16/UNC_2020_MS_TLT/depshield.svg

[snyk]: https://snyk.io/test/github/kostua16/UNC_2020_MS_TLT?targetFile=pom.xml

[snyk img]: https://snyk.io/test/github/kostua16/UNC_2020_MS_TLT/badge.svg

[dependabot]: https://dependabot.com

[dependabot img]: https://api.dependabot.com/badges/status?host=github&repo=kostua16/UNC_2020_MS_TLT

[travisci]: https://travis-ci.com/kostua16/UNC_2020_MS_TLT

[travisci img]: https://travis-ci.com/kostua16/UNC_2020_MS_TLT.svg?branch=develop

[bch]: https://bettercodehub.com/

[bch img]: https://bettercodehub.com/edge/badge/kostua16/UNC_2020_MS_TLT?branch=develop

[sonar]: https://sonarcloud.io/dashboard?id=kostua16_UNC_2020_MS_TLT

[sonar img]: https://sonarcloud.io/api/project_badges/measure?project=kostua16_UNC_2020_MS_TLT&metric=alert_status

[conventional_commits]: https://www.conventionalcommits.org/en/v1.0.0/

[google_style]: https://google.github.io/styleguide/javaguide.html

[heroku_discovery]: https://nc-edu-2020-discovery.herokuapp.com/

[heroku_config]: https://nc-edu-2020-config.herokuapp.com/

[heroku_tax]: https://nc-edu-2020-tax.herokuapp.com/

[heroku_bank]: https://nc-edu-2020-bank.herokuapp.com/

[heroku_logger]: https://nc-edu-2020-logger.herokuapp.com/

[heroku_communal]: https://nc-edu-2020-communal.herokuapp.com/

[gh_docs]: https://kostua16.github.io/UNC_2020_MS_TLT/

[gd_docs]: https://drive.google.com/file/d/1TWNZE-uM_BkHLkCsursHb-hK7jyvmy8D/view?usp=sharing
