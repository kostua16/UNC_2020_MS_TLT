module.exports = async ({github, context}) => {
    var pull = null;
    if (context.issue.number) {
        const {data: result} = await github.pulls.get({
            pull_number: context.issue.number,
            owner: context.repo.owner,
            repo: context.repo.repo,
        })
        pull = result;
    }
    if (pull && pull.number) {
        const {data: issue} = await github.issues.create({
            owner: context.repo.owner,
            repo: context.repo.repo,
            title: `[Build Failed] on [${pull.title}](${pull.number}) for commit ${context.sha}`,
            assignees: pull.assignees.map(user => user.login),
            body: `Workflow failed for commit ${context.sha}. Fails: #${context.issue.number}`
        })
        if (issue && issue.number) {
            await github.issues.createComment({
                issue_number: pull.number,
                owner: context.repo.owner,
                repo: context.repo.repo,
                body: `Depends on #${issue.number} due to build failure for commit ${context.sha}`
            })
        }
        try {
            await github.issues.addLabels({
                issue_number: pull.number,
                owner: context.repo.owner,
                repo: context.repo.repo,
                labels: ['build_failed']
            })
        } catch (except) {
            console.log(except);
        }
        try {
            await github.issues.removeLabel({
                issue_number: pull.number,
                owner: context.repo.owner,
                repo: context.repo.repo,
                name: 'build_passed'
            })
        } catch (except) {
            console.log(except);
        }

    } else {
        await github.issues.create({
            owner: context.repo.owner,
            repo: context.repo.repo,
            title: `[Build Failed] for commit ${context.sha}`,
            assignees: `${context.actor}`,
            body: `Workflow failed for commit ${context.sha}.`
        })
    }

    return null;
}
